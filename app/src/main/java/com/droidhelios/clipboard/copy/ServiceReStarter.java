package com.droidhelios.clipboard.copy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


public class ServiceReStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CopiedService", "onReceive");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, CopiedService.class));
        } else {
            context.startService(new Intent(context, CopiedService.class));
        }
    }
}