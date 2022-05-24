package com.example.booker;                         //架构重盖：
/*在程序运行开始前读取一次磁盘数据，加载至映射数组中，（前后仅两次操作文件）
        ！！！在程序内部运行时，都是对映射的数组单元做操作，最后一次性写入！！！！！

        当使用操作界面进行时，不能存储之后日期的数据，如要存则提醒不能，如此带来的好处是：可先进行一次判断是否为当前日期的存储，若是，可直接在映射数组最后添加，不是，则需要进行判断，总之在映射数组中始终是以时间为标准的一个筛选结构

        程序彻底关闭后在Ondestory 里将映射数组中的数据再次写入文件

        结论：
        问题部分：
        1.那么第一个问题就是如何在Activity之间传递当前的映射数组
        2.如何使用OnDestory回调函数
        论述部分：
        数组，与链表的优劣正式有了一个概念*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;

    private TextView go,come,to;

    private Button x,y;

    /*文件类用于读取分布类型数据*/
    private File file;
    /*该字符串组即是用来指向文件地址的*///???????????????????????????????????????????????????
    private String[] paths={"time\\month\\first_week.txt","category\\content\\"};
    private String path_count="count.txt";//具体的顺序为，总支出，总收入，总差额
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.Init_Core();

        this.money_text();

        LocalDate date=LocalDate.now();
        file=new File(getFilesDir(),paths[0]);



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
    private void Init_Core(){
        listView=findViewById(R.id.My_list);
        x=(Button) findViewById(R.id.record_B);
        y=(Button) findViewById(R.id.more);
        go=findViewById(R.id.cost);
        come=findViewById(R.id.receive);
        to=findViewById(R.id.count);
    }
    private void money_text(){
        file=new File(getFilesDir(),path_count);
        try {
            BufferedReader Br=new BufferedReader(new FileReader(file));
            go.setText(Br.readLine());      //鲁棒性很差？？？？？？？？？？？？？，具体来说并没有一种安全机制使得一定读到所需内容？？？？？？？？？？？？？？？？？？
            come.setText(Br.readLine());
            to.setText(Br.readLine());
        } catch (FileNotFoundException e) {
            Log.i("Br","can't read");
        } catch (IOException e) {
            Log.i("readline","error");
        }
    }
}