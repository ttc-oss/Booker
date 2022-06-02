package com.example.booker;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*忘记测试每月有几周*/
public class Thread_ReaDData extends Thread {

    private File rootpath;
    private Handler handler;

    private final String INDEX_time="time";
    private final String INDEX_category="category";
    private final int WIDTH_OF_DATA_SET=4;
    /*以下用来标记每一级最大存储容量的依据*/
    private final int DAYOFWEEK=7;
    private final int WEEKOFMON=6*2;
    private final int MONOFYEAR=12;
    /*种类*/
    private final int TOTAL_CATEGORY=13;
    /*message 标记：*/
    private final int TIMESTRING=0x12;
    private final int CATEGORYSTRING=0x24;

    private final String DAY_count="moncount.txt";
    private final String CATE_count="catecount.txt";
    /*四层数据架构：
    *   1.月 2.周 3.天*/

    /*数据核心：：：：*/
    private String [] [] [] [] [] data_time_core;/*【月】【周】【日】【一天所记录】【原始数据】*/
    private String [] [] [] [] data_cate_core;

    Thread_ReaDData(File rootpath,Handler handler){
        this.rootpath=rootpath;
        this.handler=handler;
    }

    @Override
    public void run() {
        super.run();

        File file =new File(rootpath,INDEX_time);
        if(file.exists()){
            File[] files= file.listFiles();

            String[][][][][] receive_da1t=new String[MONOFYEAR][][][][];   int monofyear_cou=0;

            for(int i=0;i<files.length;i++){

                if(files[i].isDirectory()){
                    File[] secondpage_file=files[i].listFiles();

                    String[][][][] receive_da2t=new String[WEEKOFMON][][][]; int weekofmon_cou=0;

                    for(int j=0;j<secondpage_file.length;j++){
                        if(secondpage_file[j].isFile()){
                            if(secondpage_file[j].getName()!=DAY_count) {
                                receive_da2t[weekofmon_cou] = this.readtime_data(secondpage_file[j]);
                                if(receive_da2t[weekofmon_cou]==null){
                                    Log.i("Thread","Something was wrong");
                                    return;
                                }
                                weekofmon_cou++;
                            }
                        }else {
                            /*There 万一添加文件夹在这里填写*/
                        }
                    }
                    String[][][][] receive_da2=new String[weekofmon_cou][][][];
                    for(int j=0;j<weekofmon_cou;j++){
                        receive_da2[j]=receive_da2t[j];
                    }

                    receive_da1t[monofyear_cou]=receive_da2;
                    monofyear_cou++;

                }else{
                    /*二级文件夹下出现的文件在此处理*/
                }

            }
            data_time_core=new String[monofyear_cou][][][][];
            for(int i=0;i<monofyear_cou;i++){
                data_time_core[i]=receive_da1t[i];
            }
        }

        Message message=Message.obtain();
        message.obj=data_time_core;
        message.what=TIMESTRING;
        handler.handleMessage(message);
        /*至这里以时间顺序的读取完成,并且数据传回service*/
        /*种类读取*/
        file =new File(rootpath,INDEX_category);
        if(file.exists()){
            String [][][][] receive_cate1t=new String[TOTAL_CATEGORY][][][]; int cate1_cou=0;
            File[] files=file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    String[][][] receive_cate2t=new String[MONOFYEAR][][];
                    File[]  secondpage_file=files[i].listFiles();  int cate2_cou=0;
                    for(int j=0;j<secondpage_file.length;j++){
                        if(secondpage_file[j].isFile()){
                            if(secondpage_file[j].getName()!=CATE_count){
                                receive_cate2t[cate2_cou]=readcate_data(secondpage_file[j]);
                                cate2_cou++;
                            }
                        }
                    }
                    String[][][] receive_cate2=new String[cate2_cou][][];
                    for(int j=0;j<cate2_cou;j++){
                        receive_cate2[j]=receive_cate2t[j];
                    }
                    receive_cate1t[cate1_cou]=receive_cate2;
                    cate1_cou++;
                }
            }
            data_cate_core=new String[cate1_cou][][][];
            for(int i=0;i<cate1_cou;i++){
                data_cate_core[i]=receive_cate1t[i];
            }
        }

        message=Message.obtain();
        message.obj=data_cate_core;
        message.what=CATEGORYSTRING;
        handler.handleMessage(message);

        Log.i("Thread","complete");
    }



    private String[][][] readtime_data(File file){
        try {
            BufferedReader Br=new BufferedReader(new FileReader(file));
            String t=Br.readLine();String date_comp=t;

            String[][] stringdate_temp=new String[10][WIDTH_OF_DATA_SET];
            String[][][] stringdayofweek_temp=new String[DAYOFWEEK][][];
            int readdate_cou=0; int readdayofweek_cou=0;

            while(t!=null){/*分级处理，用函数。。。。。。。。。。。。。。。。。。。。。。。*/
                if(t!=date_comp){
                    String[][] date_finally=new String[readdate_cou][WIDTH_OF_DATA_SET];
                    for(int i=0;i<readdate_cou;i++){
                        date_finally[i]=stringdate_temp[i];
                    }
                    stringdayofweek_temp[readdayofweek_cou]=date_finally;
                    readdayofweek_cou++;
                    stringdate_temp=new String[10][WIDTH_OF_DATA_SET];
                    readdate_cou=0;
                }
                date_comp=t;
                stringdate_temp[readdate_cou][0]=t;
                for(int i=1;i<WIDTH_OF_DATA_SET;i++){
                    stringdate_temp[readdate_cou][i]=Br.readLine();
                }
                readdate_cou++;
            }

            String[][] date_finally=new String[readdate_cou][WIDTH_OF_DATA_SET];
            for(int i=0;i<readdate_cou;i++){
                date_finally[i]=stringdate_temp[i];
            }
            stringdayofweek_temp[readdayofweek_cou]=date_finally;
            readdayofweek_cou++;

            String[][][] return_data=new String[readdayofweek_cou][][];

            for(int i=0;i<readdayofweek_cou;i++){
                return_data[i]=stringdayofweek_temp[i];
            }

            return return_data;

        } catch (FileNotFoundException e) {
            Log.i("Thread","can't read");
            /*需要继续添加使得程序跟家稳定。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。*/
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Thread","takeno");
            /*同上*/
        }
        return null;
    }

    private String[][] readcate_data(File file){
        String[][] data=null;
        try {
            BufferedReader Br=new BufferedReader(new FileReader(file));
            String[][] temp=new String[50][WIDTH_OF_DATA_SET];//TODO:这个可以暂定这个值码
            int temp_cou=0;
            String t=Br.readLine();
            while(t!=null){
                temp[temp_cou][0]=t;
                for(int i=0;i<WIDTH_OF_DATA_SET;i++){
                    temp[temp_cou][i]=Br.readLine();
                }
                temp_cou++;

                t=Br.readLine();
            }
            data=new String[temp_cou][];
            for(int i=0;i<temp_cou;i++){
                data[i]=temp[i];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
