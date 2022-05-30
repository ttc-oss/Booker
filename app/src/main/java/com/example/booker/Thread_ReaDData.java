package com.example.booker;

import java.io.File;

public class Thread_ReaDData extends Thread {

    private File file;
    private File rootpath;

    private final String INDEX_time="time";
    private final String INDEX_category="time";


    Thread_ReaDData(File rootpath){
        this.rootpath=rootpath;
    }

    @Override
    public void run() {
        super.run();

        file =new File(rootpath,INDEX_time);
        if(file.exists()){
            File[] files= file.listFiles();
        }


    }
}
