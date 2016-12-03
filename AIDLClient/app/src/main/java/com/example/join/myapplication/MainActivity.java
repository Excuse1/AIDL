package com.example.join.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.join.aidltest.Calculation;


public class MainActivity extends AppCompatActivity {
    private EditText etOne, etTwo;
    private Button btn;
    private TextView tv;
    private Calculation mCalculation;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        etOne = (EditText) findViewById(R.id.etone);
        etTwo = (EditText) findViewById(R.id.ettwo);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        //连接远程服务
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
               mCalculation = Calculation.Stub.asInterface(service);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCalculation = null;
            }
        };
        //使用意图对象绑定开启服务
        Intent intent = new Intent();

        intent.setAction("com.dapeng.aidldemo.CalculateService");
        //在5.0及以上版本必须要加上这个
        intent.setPackage("com.example.join.aidltest");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etOne.getText().toString();
                String s1 = etTwo.getText().toString();
                if (TextUtils.isEmpty(s) && TextUtils.isEmpty(s1)) {
                    Toast.makeText(MainActivity.this, "您输入的数字不合法", Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        int addition = mCalculation.addition(Integer.parseInt(s), Integer.parseInt(s1));
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("调用远程服务获取到的计算结果是==");
                        stringBuilder.append(addition);
                        tv.setText(stringBuilder.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
