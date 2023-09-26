package com.example.myvpn;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.net.VpnService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;
import java.io.IOException;

public class MyVPNService extends VpnService{
    protected final String ANDROID_CHANNEL_ID = "notification_channel_my_vpn_service";
    protected final int START_NOTIFICATION_ID = 100;

    ParcelFileDescriptor mParcelFileDescriptor;

    public MyVPNService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationChannel notificationChannel = new NotificationChannel(
                ANDROID_CHANNEL_ID, "VPN Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification.Builder nBuilder = new Notification.Builder(this, ANDROID_CHANNEL_ID);
        nBuilder.setStyle(new Notification.BigTextStyle());
        nBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        nBuilder.setOngoing(true);
        nBuilder.setContentText("VPN Service is running");
        Notification notification = nBuilder.build();
        startForeground(START_NOTIFICATION_ID, notification);

        Logger.d("Notification created.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret = super.onStartCommand(intent, flags, startId);
        Logger.d("********** Vpn Service started ********");

        try {
            VpnService.Builder builder = new VpnService.Builder()
                    .setMtu(1500)
                    .addAddress("192.168.1.10", 24)
                    .addDnsServer("8.8.8.8");
            mParcelFileDescriptor = builder.establish();
        }
        catch (Exception ex){
            Logger.ex(ex);
        }

        return ret;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mParcelFileDescriptor){
            try {
                mParcelFileDescriptor.close();
            } catch (Exception e) {
                Logger.ex(e);
            }
        }
    }
}