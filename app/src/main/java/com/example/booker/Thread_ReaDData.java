package com.example.booker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Handler;

/*忘记测试每月有几周*/
public class Thread_ReaDData extends Thread {

    public Handler handler;
    private File rootpath;

    private final String INDEX_time="time";
    private final String INDEX_category="category";
    private final int WIDTH_OF_DATA_SET=4;
    /*以下用来标记每一级最大存储容量的依据*/
    private final int DAYOFWEEK=7;
    private final int WEEKOFMON=6;
    private final int MONOFYEAR=12;

    private final String path_count="moncount.txt";
    /*四层数据架构：
    *   1.月 2.周 3.天*/

    /*数据核心：：：：*/
    private String [] [] [] [] [] data_core;/*【月】【周】【日】【一天所记录】【原始数据】*/

    Thread_ReaDData(File rootpath){
        this.rootpath=rootpath;
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
                            if(secondpage_file[j].getName()!=path_count) {
                                receive_da2t[weekofmon_cou] = this.read_data(secondpage_file[j]);
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
            data_core=new String[monofyear_cou][][][][];
            for(int i=0;i<monofyear_cou;i++){
                data_core[i]=receive_da1t[i];
            }
        }

        /*至这里以时间顺序的读取完成*/



    }



    private String[][][] read_data(File file){
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
}
