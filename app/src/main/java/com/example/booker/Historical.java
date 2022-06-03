package com.example.booker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*TODO:需要添加按键响应*/
public class Historical extends AppCompatActivity {

    private String[][][][][] time_data_core;
    private String[][][][] category_data_core;

    private int[][] switch_of_timedata;

    private ExpandableListView listView;


    private final int WEEKOFMON=6*2;
    /*用来记录每月开支总计*/
    private String[][] month_total=new String[time_data_core.length][2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        getSupportActionBar().hide();

        this.ini_data_soft();
        this.checkthetimedata();


    }
    private void ini_data_soft(){
        listView=findViewById(R.id.My_list);

        Intent get=getIntent();
        time_data_core= (String[][][][][]) get.getSerializableExtra("dataoftime");
    }

    private void checkthetimedata(){
        int[][] temp=new int[time_data_core.length][WEEKOFMON];
        for(int i=0;i<time_data_core.length;i++){
            int cou=0;
            for(int j=0;j<time_data_core[i].length;j++){
                if(time_data_core[i][j][0][0][1]=="工资"||time_data_core[i][j][0][0][1]=="奖金") {
                    temp[i][cou] = j;
                    cou++;
                }
            }
            int [] temps=new int[cou];
            for(int j=0;j<cou;j++){
                temps[j]=temp[i][j];
            }
            temp[i]=temps;
        }
        switch_of_timedata=temp;
    }

    private void filemonth_read(){
        for(int i=0;i<time_data_core.length;i++) {
            File file = new File(getFilesDir(),"time\\"+i+1+"月\\moncount.txt");
            try {
                BufferedReader Br=new BufferedReader(new FileReader(file));
                month_total[i][0]=Br.readLine();
                month_total[i][1]=Br.readLine();
                Br.close();
            } catch (FileNotFoundException e) {
                /*this way*/
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /*这里是为了和外界的继承类隔开，第一版使用*/
    //TODO:将这里填充完毕
    private class This_Adapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return time_data_core.length;
        }

        @Override
        public int getChildrenCount(int i) {
            int sum=0;
            for(int j=0;j<time_data_core[i].length;j++){
                for(int x=0;x<time_data_core[i][j].length;x++){
                    sum+=time_data_core[i][j][x].length;
                }
            }
            return sum;
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public Object getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolder_p parent;
            if(view==null){
                parent=new ViewHolder_p();
                view= LayoutInflater.from(Historical.this).inflate(R.layout.listview_parent,null);
                parent.in=view.findViewById(R.id.list_group_in);
                parent.out=view.findViewById(R.id.list_group_out);
                parent.monthvalue=view.findViewById(R.id.list_group_month);
                view.setTag(parent);
            }else{
                parent= (ViewHolder_p) view.getTag();
            }
            parent.in.setText(month_total[i][1]);
            parent.out.setText(month_total[i][0]);
            String t=time_data_core[i][0][0][0][0].split("-")[1];
            parent.monthvalue.setText(t+"月");
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            ViewHolder_c child;
            if(view==null){
                child=new ViewHolder_c();
                view= LayoutInflater.from(Historical.this).inflate(R.layout.listview_children,null);
                child.date=view.findViewById(R.id.list_child_day);
                child.weekaday=view.findViewById(R.id.list_child_weekaday);
                child.textView_account=view.findViewById(R.id.list_child_account);
                child.textView_cate=view.findViewById(R.id.list_child_describe);
                child.item_cost=view.findViewById(R.id.list_child_money);
                view.setTag(child);
            }else{
                child= (ViewHolder_c) view.getTag();
            }//TODO:可以采取拼接后完成
            child.date.setText(time_data_core[i][]);
            child.weekaday.setText();
            child.textView_account.setText();
            child.textView_cate.setText();
            child.item_cost.setText();
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
        private class ViewHolder_p{
            TextView monthvalue,out,in;
        }
        private class ViewHolder_c{
            TextView date,weekaday,textView_cate,textView_account,item_cost;
        }
    }
}