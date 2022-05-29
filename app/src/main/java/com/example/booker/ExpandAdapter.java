package com.example.booker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Calendar;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private String[][][] task;
    private Context context;



    private final int TIME_withSQ=0;
    private final int CATE_withSQ=1;
    private final int ACCOUNT_withSQ=2;
    private final int DEAL_withSQ=3;

    ExpandAdapter(String[][][] input, Context context){
        this.task=input;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return task.length;

    }

    @Override
    public int getChildrenCount(int i) {
        return task[i].length;
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
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i*256+i1;
    }

    @Override
    public boolean hasStableIds() {             //这个东西干什么用？？？？？？？？？？？？？？？？
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        ViewHolder_p parent;
        if(view ==null){
            view=LayoutInflater.from(context).inflate(R.layout.expandlistview_main_pa,null);
            parent=new ViewHolder_p();
            parent.countbox=view.findViewById(R.id.pa_out);
            parent.timebox=view.findViewById(R.id.pa_time);
            view.setTag(parent);
        }else {
            parent= (ViewHolder_p) view.getTag();
        }

        parent.countbox.setText(this.compute_dayofcou(i));
        parent.timebox.setText(this.task[i][0][TIME_withSQ]);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        ViewHolder_c child;
        if(view==null){
            child=new ViewHolder_c();
            view=LayoutInflater.from(context).inflate(R.layout.expandlistview_main_ch,null);//第三参数
            child.account=view.findViewById(R.id.list_main_ch_account);
            child.category=view.findViewById(R.id.list_main_ch_cate);
            child.deal=view.findViewById(R.id.list_child_money);
            view.setTag(child);
        }else {
            child=(ViewHolder_c) view.getTag();
        }

        child.account.setText(task[i][i1][ACCOUNT_withSQ]);
        child.deal.setText(task[i][i1][DEAL_withSQ]);
        child.category.setText(task[i][i1][CATE_withSQ]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



    private class ViewHolder_p{
        TextView timebox,countbox;
    }
    private class ViewHolder_c{
        TextView category,account,deal;
    }



    private String compute_dayofcou(int i){

        double cou=0;

        for(int j=0;j<task[i].length;j++){
            cou+=Double.parseDouble(task[i][j][DEAL_withSQ]);
        }

        return String.valueOf(cou);
    }

}
