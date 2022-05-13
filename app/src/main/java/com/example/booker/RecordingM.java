package com.example.booker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class RecordingM extends AppCompatActivity {
    String waYS,CATE,money;                        //记录存储类型

    View CORE;                          //加载复用的视图
    LinearLayout RooT;                   //当前视图
    File My_file;                           //将要使用的文件

    private  RadioGroup chiose;                      //选择的空间
    private  Spinner cate,ways;
    private  Button Graph,Sure,tiMes,note;
    private  TextView MONEY_1;
    private  String[] category_1,category_2,account;                          //两个种类spinner
    private  TextView date_s;
    private  ImageView picture;

    private int YEAR,MONTH,DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_m);


        RooT=(LinearLayout) findViewById(R.id.thsi);              //终于成了》》》》》》》》》》》最后利用Viewgroup的方法成了       //第一部分将视图加载
        CORE=getLayoutInflater().inflate(R.layout.activity_recording,null);//遗留问题：setcontentview 为什么不能用？？？？？？？？？？？？？？？？？？？？？？？
        RooT.addView(CORE,0);
        category_1= new String[]{"餐饮", "日用", "服饰", "美容", "零食", "饮料", "美容", "零食", "饮料", "汽车", "住房"};
        category_2= new String[]{"工资", "奖金"};
        account=new String[]{"现金","信用卡","储蓄卡"};


        chiose=(RadioGroup)findViewById(R.id.RECORDING_way) ;           //我是傻逼，加载顺序都出现了致命问题！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        cate=(Spinner)findViewById(R.id.cate);                          //第二部分：将控件加载至后台
        ways=(Spinner)findViewById(R.id.way) ;

        tiMes=(Button) findViewById(R.id.timea) ;
        note=(Button)findViewById(R.id.sas) ;
        Graph=(Button)findViewById(R.id.Catch) ;
        Sure=(Button)findViewById(R.id.Sa_Ve) ;

        date_s=(TextView)findViewById(R.id.date_picker_actions) ;
        MONEY_1=(TextView)findViewById(R.id.money_R);

        Calendar cal =Calendar.getInstance();                       //获取当前时间
        YEAR=cal.get(Calendar.YEAR);
        MONTH=cal.get(Calendar.MONTH);
        DAY=cal.get(Calendar.DAY_OF_MONTH);

        date_s.setText(YEAR+"-"+(MONTH+1)+"-"+DAY);

        ArrayAdapter<String> x=new ArrayAdapter<String>(RecordingM.this,android.R.layout.simple_list_item_1,account);       //账户填充
        ways.setAdapter(x);

        chiose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                waYS=((RadioButton)findViewById(i)).getText().toString();                   //第三部分：将Spinner 填充
                if(waYS.equals("支出")){
                    ArrayAdapter<String> t=new ArrayAdapter<String>(RecordingM.this,android.R.layout.simple_list_item_1,category_1);
                    cate.setAdapter(t);
                }else {
                    ArrayAdapter<String> t=new ArrayAdapter<String>(RecordingM.this,android.R.layout.simple_list_item_1,category_2);
                    cate.setAdapter(t);
                }
            }
        });

        Graph.setOnClickListener(new View.OnClickListener() {                           //重改Onclick事件写法//暂时搁置，等之后再上线
            @Override
            public void onClick(View view) {
/*                Intent take=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                start*/
                Toast.makeText(RecordingM.this, "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RecordingM.this, "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        tiMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date_s.setText(year+"-"+(++month)+"-"+day);
                    }
                };
                DatePickerDialog t=new DatePickerDialog(RecordingM.this,0,listener,YEAR,MONTH,DAY);
                t.show();
            }
        });

        Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(waYS!=null){
                    CATE=cate.getSelectedItem().toString();
                    money=MONEY_1.getText().toString();
                    if(money.equals("")==false) {
                        if (waYS.equals("支出")) {
                            My_file = new File(getExternalFilesDir("test"), "tab_1.txt");

                            Saveing(My_file);
                        } else if (waYS.equals("收入")) {
                            My_file = new File(getExternalFilesDir("test"), "Tab_2.txt");

                            Saveing(My_file);
                        }
                    }else{
                        Toast.makeText(RecordingM.this, "未输入金额", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RecordingM.this, "请选择’支出‘或‘收入’", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Saveing(File file){
        if(file.exists()){

        }else{
            try{
                file.createNewFile();
            }catch (IOException e){
                Toast.makeText(this, "无法存储，请检查存储路径是否允许", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            FileWriter FW = new FileWriter(file);
            FW.write(waYS + "\n");                                                                      //6项依次写入
            FW.write(CATE + "\n");
            FW.write(ways.getSelectedItem().toString() + "\n");
            FW.write(date_s.getText().toString() + "\n");
            FW.write(money+"\n");
            FW.write("\n");                                                                             //留给备注的

            FW.flush();
            FW.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "数据流出现错误请联系开发人员", Toast.LENGTH_SHORT).show();
        }
    }
}