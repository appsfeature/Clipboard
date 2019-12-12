package com.droidhelios.clipboard.util;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.droidhelios.clipboard.copy.CopiedService;
import com.droidhelios.clipboard.floating.FloatingViewService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ClipboardUtil {


    public static void startCopyService(Context context) {
        if (!isServiceRunning(context, CopiedService.class)) {
            context.startService(new Intent(context, CopiedService.class));
        }
    }

    public static void stopService(Context context, Class service) {
        if (isServiceRunning(context, service)) {
            context.stopService(new Intent(context, service));
        }
    }

    public static List<String> getClipboardList(Context context) {
        ClipboardManager   myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        List<String> mList = new ArrayList<>();
        if (myClipboard != null) {
            ClipData clipData = myClipboard.getPrimaryClip();
            if(clipData!=null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    String text = item.getText().toString();
                    mList.add(text);
                }
            }
        }
        return mList;
    }


    public static void copy(Context context, String stripped) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("content", stripped);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context,"Text Copied", Toast.LENGTH_SHORT).show();
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        if (myClipboard != null) {
            myClipboard.setPrimaryClip(myClip);
        }
        Toast.makeText(context, "Text Copied", Toast.LENGTH_SHORT).show();
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(manager!=null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void sendBroadcastMessage(Context context) {
        Intent intent = new Intent();
        intent.setAction(FloatingViewService.ACTION_BROADCAST);
        context.sendBroadcast(intent);
    }

}
