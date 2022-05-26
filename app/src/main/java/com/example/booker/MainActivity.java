package com.example.booker;
/*在程序运行开始前读取一次磁盘数据，加载至映射数组中，（前后仅两次操作文件）
        当使用操作界面进行时，不能存储之后日期的数据，如要存则提醒不能，如此带来的好处是：可先进行一次判断是否为当前日期的存储，若是，可直接在映射数组最后添加，不是，则需要进行判断，总之在映射数组中始终是以时间为标准的一个筛选结构

           比较字符串的哈希值和直接比较字符串？？？？
        结论：
        问题部分：
        1.那么第一个问题就是如何在Activity之间传递当前的映射数组

        顺序：
        1.时间
        2.种类
        3.账户
        4.金额

        */

/*现在应该可以认为数据提取较为准确了，    下一步投射至list view 创建线程读取数据*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //////////////////////////当前周or上周的总体数据,但不确定是否为4各一组？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
    private final int WIDTH_OF_DATA_SET=4;
    /*这里首先默认数据存储时，时间最近的在第一项*//*声明一天最多记20条！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！*/
    private String[][][] node=new String[8][][];     //应该可以用内部类包装一下。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
    private int allcou=0;


    private ExpandableListView listView;

    private TextView go,come,to;

    private Button x,y;

    /*文件类用于读取分布类型数据*/
    private File file;

    private String month_in;
    private String week_of_month;
    private String DaTe;
    private String[] DaTes=new String[3];
    private final String path_count="moncount.txt";//具体的顺序为，总支出，总收入
    private final String file_out="_weeksequence_out.txt";
    private final String file_in="_weeksequence_int.txt";
    /*当前日期*/
    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.Init_Core();

        date=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DaTe=format.format(date.getTime());
        month_in=String.valueOf(date.get(Calendar.MONTH));//直接获取当前日期月份的值？？

        this.money_text(this.month_in);

        this.read_by();

        this.setdata();

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent F_To=new Intent(MainActivity.this,RecordingM.class);
                startActivity(F_To);
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    /*初始化控件*/
    private void Init_Core(){
        listView=findViewById(R.id.My_list);
        x=(Button) findViewById(R.id.record_B);
        y=(Button) findViewById(R.id.more);
        go=findViewById(R.id.cost);
        come=findViewById(R.id.receive);
        to=findViewById(R.id.count);
    }
    /*头部三个文本框通过外界文件写入*/
    private void money_text(String mon) {
        file = new File(getFilesDir(), "time\\" + mon + "月\\" + path_count);
        if (file.exists()) {
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.i("create", "NO");
            }
        }
        try {
            BufferedReader Br = new BufferedReader(new FileReader(file));
            go.setText(Br.readLine());      //鲁棒性很差？？？？？？？？？？？？？，具体来说并没有一种安全机制使得一定读到所需内容？？？？？？？？？？？？？？？？？？
            come.setText(Br.readLine());
            to.setText(Br.readLine());
            Br.close();
        } catch (FileNotFoundException e) {
            Log.i("Br", "can't read");
        } catch (IOException e) {
            Log.i("readline", "error");
        }
    }
    /*扩充存储单元*/
/*    private String[][] expand(String com){//////////////起名问题？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        String[][] re=new String[come.length()+100][WIDTH_OF_DATA_SET];
        for(int i=0;i<length;i++){
            re[i]=node[i];
        }
        return re;
    }*/



    /*详细信息读取*/
    private void read_by(){                     //整理解析该函数。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。

        /*这一部分用来判定是否读取文件时，需要切换路径，顺便将”今日“的周数读出,再者将所需的日期字符串读取出来*/
        int compare_week=date.get(Calendar.WEEK_OF_MONTH);
        week_of_month=String.valueOf(compare_week);//先假定返回一个阿拉伯数字！！！！！！！！！！！！！！！！！！！！！
        /*布尔式是用来告诉是否文件需要切换*/
        Boolean file_switch=true;
        Calendar cdate= (Calendar) date.clone();
        String item_mon=month_in;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for(int j=0;j<3;j++){
            cdate.add(Calendar.DATE,-j);
            DaTes[j]=format.format(cdate.getTime());
            if(cdate.get(Calendar.WEEK_OF_MONTH)<compare_week){
                file_switch=false;
                break;
            }else if(cdate.get(Calendar.WEEK_OF_MONTH)>compare_week){
                item_mon=String.valueOf(Integer.parseInt(month_in)-1);
                file_switch=false;
                break;
            }
        }



        /*第二部分根据判定开始读取数据*/
        if(file_switch){                            //改吧，明显写的复用率极低。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。存在大量的临时方案

            file=new File(getFilesDir(),"time\\"+month_in+"月\\"+week_of_month+file_out);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }

            allcou=4;

            file=new File(getFilesDir(),"time\\"+month_in+"月\\"+week_of_month+file_in);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }
        }else {

            file=new File(getFilesDir(),"time\\"+month_in+"月\\"+week_of_month+file_out);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }

            file=new File(getFilesDir(),"time\\"+item_mon+"月\\"+cdate.get(Calendar.WEEK_OF_MONTH)+file_out);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }

            allcou=4;

            file=new File(getFilesDir(),"time\\"+month_in+"月\\"+week_of_month+file_in);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }

            file=new File(getFilesDir(),"time\\"+item_mon+"月\\"+cdate.get(Calendar.WEEK_OF_MONTH)+file_in);

            if(file.exists()){
                this.read_core(file);
            }else {
                Toast.makeText(this, "未找到最近的数据", Toast.LENGTH_SHORT).show();
            }
        }

    }





    private void read_core(File filename){            //好愚蠢

        try {

            BufferedReader Br=new BufferedReader(new FileReader(filename));
            String t=Br.readLine(); String clo=t;

            String[][] temp=new String[20][WIDTH_OF_DATA_SET];
            int cou=0;

            while(t==DaTe||t==DaTes[0]||t==DaTes[1]||t==DaTes[2]){
                /*读取数据*/
                if(t!=clo){
                    node[allcou]=temp;
                    allcou++;
                    temp=new String[20][WIDTH_OF_DATA_SET];
                    cou=0;
                }
                clo=t;
                try {
                    temp[cou][0] = t;
                } catch (IndexOutOfBoundsException e) {
                    //temp=this.expand(temp);//下次再想？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
                    temp[cou][0] = t;
                }
                for (int i = 1; i < WIDTH_OF_DATA_SET; i++) {
                    temp[cou][i] = Br.readLine();
                }
                cou++;
                /*重复写入以尝试*/
                t=Br.readLine();
            }
            Br.close();
            Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {                 //鲁棒性极差。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
            Log.i("haha","haha");
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.i("hat","not at all");
            Toast.makeText(this, "fuck", Toast.LENGTH_SHORT).show();
        }
    }

    private void setdata(){
        /*this way*/
    }


}