package com.example.booker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

    private String[][]Group_parent;
    private String[]Range,Weekaday;
    private Context mcontext;
    private int count;

    private static class viewholder_p{
        private TextView when,describe,GoTo,into;
    }
    private static class viewholder_c{
        private TextView day,weekaday,des,account,much;
    }

    public MyAdapter(String[][]GROUP_Parent,String[]range,String[]weekaday,int count,Context con){//剧额参数消去？？？？？？？？？？？？？？？？？？？？？？？？？？？
        this.Group_parent=GROUP_Parent;
        this.Range=range;
        this.Weekaday=weekaday;
        this.mcontext=con;
        this.count=count;
    }

    @Override
    public int getGroupCount() {                                //以下统一用describe来描述
        return this.list_parent_describecat.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return this.list_child_describe[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return this.list_parent_describecat[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.list_child_describe[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {                 //????????????????????????????????????????????????这是什么
        return false;
    }

    @Override
    public View getGroupView(int GP, boolean IsExpanded, View view, ViewGroup viewGroup) {

        viewholder_p t;
        if(view==null){
            view= LayoutInflater.from(mcontext).inflate(R.layout.listview_parent,viewGroup,false);
            t=new viewholder_p();
            t.when=(TextView) view.findViewById(R.id.list_group_when);
            t.describe=(TextView) view.findViewById(R.id.list_group_describe);
            t.GoTo=(TextView) view.findViewById(R.id.list_group_goto);
            t.into=(TextView) view.findViewById(R.id.list_group_into);
            view.setTag(t);
        }else{
            t=(viewholder_p) view.getTag();
        }
        t.when.setText(list_parent_when[GP]);
        t.describe.setText(list_parent_describecat[GP]);
        t.into.setText(list_parent_into[GP]);
        t.GoTo.setText(list_parent_goto[GP]);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        viewholder_c t;
        if(view==null){
            view=LayoutInflater.from(mcontext).inflate(R.layout.listview_children,viewGroup,false);
            t=new viewholder_c();
            t.account=(TextView) view.findViewById(R.id.list_child_account);
            t.day=(TextView) view.findViewById(R.id.list_child_day);
            t.des=(TextView) view.findViewById(R.id.list_child_describe);
            t.weekaday=(TextView) view.findViewById(R.id.list_child_weekaday);
            t.much=(TextView) view.findViewById(R.id.list_child_money);
            view.setTag(t);
        }else{
            t=(viewholder_c) view.getTag();
        }
        t.account.setText(list_child_account[i][i1]);
        t.much.setText(list_child_money[i][i1]);
        t.des.setText(list_child_describe[i][i1]);
        t.weekaday.setText(list_child_weekaday[i][i1]);
        t.day.setText(list_child_day[i][i1]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
