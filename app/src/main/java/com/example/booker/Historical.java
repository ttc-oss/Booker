package com.example.booker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
/*TODO:需要添加按键响应*/
public class Historical extends AppCompatActivity {

    private String[][][][][] time_data_core;
    private ExpandableListView listView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        getSupportActionBar().hide();

        this.ini_data_soft();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent=new Intent(Historical.this,RecordingM.class);
                startActivity(mintent);
            }
        });

    }
    private void ini_data_soft(){
        listView=findViewById(R.id.My_list);
        button=findViewById(R.id.record_B);

        Intent get=getIntent();
        time_data_core= (String[][][][][]) get.getSerializableExtra("dataoftime");
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
            return null;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
        private class ViewHolder_p{

        }
        private class ViewHolder_c{

        }
    }
}