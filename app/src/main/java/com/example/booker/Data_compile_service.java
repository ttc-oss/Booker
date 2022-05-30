package com.example.booker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Data_compile_service extends Service {
    public Data_compile_service() {


    }
    @Override
    public void onCreate() {
        super.onCreate();
        Thread_ReaDData thread=new Thread_ReaDData(getFilesDir());
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}