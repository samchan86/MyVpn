package com.example.myvpn;

import android.util.Log;
import java.util.Objects;

public class Logger {

    private static final String TAG = "CPG_VPN";
    private static final boolean DEBUG = true;

    protected static void d(String msg){
        if(DEBUG){
            Log.d(TAG, msg);
        }
    }

    protected static void e(String msg){
        Log.e(TAG, msg);
    }

    protected static void ex(Exception ex){
       e(Objects.requireNonNull(ex.getMessage()));
        if(DEBUG){
            ex.printStackTrace();
        }
    }
}
