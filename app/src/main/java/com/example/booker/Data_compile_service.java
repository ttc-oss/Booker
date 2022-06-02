package com.example.booker;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class Data_compile_service extends Service {
    private final int TIMESTRING=0x12;
    private final int CATEGORYSTRING=0x24;


    public String[][][][][] time_of_data=null;
    public String[][][][] category_of_data=null;

    //TODO：最后决定先将后台中的数据传递给前台，使前台判断是否可进入下一项
    //TODO:关于种类这一项的数据，需要思考什么时候必须用到（即它的最大延迟时间）

    private Handler handler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(msg.what==TIMESTRING){
                time_of_data=(String[][][][][]) msg.obj;
            }else if(msg.what==CATEGORYSTRING){
                category_of_data=(String[][][][]) msg.obj;
            }

        }
    };

    public Data_compile_service() {
    }
    @Override
    public void onCreate() {

        super.onCreate();
        Thread_ReaDData thread=new Thread_ReaDData(getFilesDir(),handler);
        thread.start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}