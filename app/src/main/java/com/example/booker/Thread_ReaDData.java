package com.example.booker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*忘记测试每月有几周*/
public class Thread_ReaDData extends Thread {

    private File file;
    private File rootpath;

    private final String INDEX_time="time";
    private final String INDEX_category="time";
    private final int WIDTH_OF_DATA_SET=4;

    /*四层数据架构：
    *   1.月 2.周 3.天*/
    private String [][][][][] data_core;

    Thread_ReaDData(File rootpath){
        this.rootpath=rootpath;
    }

    @Override
    public void run() {
        super.run();

        file =new File(rootpath,INDEX_time);
        if(file.exists()){
            File[] files= file.listFiles();
            for(int i=0;i<files.length;i++){

                if(files[i].isDirectory()){
                    File[] secondpage_file=files[i].listFiles();
                    for(int j=0;j<secondpage_file.length;j++){
                        if(secondpage_file[j].isFile()){
                            this.read_data(secondpage_file[j]);
                        }else {

                        }
                    }
                }

            }
        }


    }
    private void read_data(File file){
        try {
            BufferedReader Br=new BufferedReader(new FileReader(file));
            String t=Br.readLine();String date_comp=t;

            String[][] stringdate_temp=new String[10][WIDTH_OF_DATA_SET];
            String[][][] stringdayofweek_temp=new String[7][][];
            String[][][][] stringweekofmon_temp=new String[5][][][];
            String[][][][][] stringmonofyear_temp=new String[12][][][][];
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

        } catch (FileNotFoundException e) {
            Log.i("Thread","can't read");
            /*需要继续添加使得程序跟家稳定。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。*/
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Thread","takeno");
            /*同上*/
        }
    }
}
