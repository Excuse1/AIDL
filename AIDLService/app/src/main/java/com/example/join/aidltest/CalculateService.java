package com.example.join.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by join on 2016/11/20.
 */
public class CalculateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("--", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("--", "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("--", "onStart");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("--", "onUnbind");
        return super.onUnbind(intent);
    }



    Calculation.Stub mBinder = new Calculation.Stub() {
        @Override
        public int addition(int a, int b) throws RemoteException {
        return a+b;
        }
    };
}
