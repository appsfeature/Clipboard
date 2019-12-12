package com.droidhelios.clipboard.copy;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.droidhelios.clipboard.AppApplication;
import com.droidhelios.clipboard.database.Clipboard;
import com.droidhelios.clipboard.floating.FloatingViewService;
import com.droidhelios.clipboard.util.ClipboardUtil;
import com.droidhelios.clipboard.util.SharedPrefUtil;
import com.droidhelios.clipboard.util.TaskRunner;


public class CopiedService extends Service {
    private static final String TAG = CopiedService.class.getName();

    private ClipboardManager.OnPrimaryClipChangedListener clipListener;
    private ClipboardManager clipboardManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipListener = new ClipboardListener();
        clipboardManager.addPrimaryClipChangedListener(clipListener);
    }


    private void startCopiedActivity(String sentence) {
        if (!SharedPrefUtil.isEnableAutoCopy()) {
            Intent intent = new Intent(this, CopiedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CopiedActivity.EXTRA_COPIED_TEXT, sentence);
            this.startActivity(intent);
        } else {
            AppApplication.databaseManager.insert(new Clipboard(sentence), new TaskRunner.Callback<Long>() {
                @Override
                public void onComplete(Long result) {
                    ClipboardUtil.sendBroadcastMessage(getApplicationContext());
                }
            });
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onCreate");
        return null;
    }


    @Override
    public void onDestroy() {
        if (clipboardManager != null && clipListener != null) {
            clipboardManager.removePrimaryClipChangedListener(clipListener);
        }
        Log.d(TAG, "onDestroy");
        super.onDestroy();

        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
    }

    class ClipboardListener implements ClipboardManager.OnPrimaryClipChangedListener {

        @Override
        public void onPrimaryClipChanged() {
            ClipData data = clipboardManager.getPrimaryClip();
            if (data != null && data.getItemAt(0) != null) {
                CharSequence charSequence = data.getItemAt(0).getText();
                if (charSequence != null) {
                    startCopiedActivity(charSequence.toString());
//                    if(settingPref.isCopyToTranslate()) {
//                    }else{
//                        stopSelf();
//                    }
                }
            }
        }
    }
}