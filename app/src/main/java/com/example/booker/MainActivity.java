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
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x=(Button) findViewById(R.id.SAVE);
        y=(Button) findViewById(R.id.HISTORT);

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
}