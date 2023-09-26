package com.example.myvpn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 123456;

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceIntent = new Intent(this, MyVPNService.class);

        Intent startIntent = VpnService.prepare(this);
        if(null != startIntent){
            startActivityForResult( startIntent, REQUEST_CODE, null);
        }
        else {
            Logger.d("VPN service permission already granted.");
            startForegroundService(mServiceIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_CODE == requestCode){
            Logger.d("VPN service permission granted");
            startForegroundService(mServiceIntent);
        }
        else {
            Logger.e("VPN service permission NOT granted");
            finishAndRemoveTask();
        }
    }
}