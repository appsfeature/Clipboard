package com.droidhelios.clipboard.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.droidhelios.clipboard.listeners.Response;
import com.droidhelios.clipboard.util.TaskRunner;

import java.util.List;
import java.util.concurrent.Callable;

public class DatabaseManager {

    public static ClipboardDatabase appDatabase;
    private final Context context;

    private DatabaseManager(Context context) {
        this.context = context;
        appDatabase = getAppDatabase(context);
    }

    public static DatabaseManager newInstance(Context context) {
        return new DatabaseManager(context);
    }

    public ClipboardDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = ClipboardDatabase.create(context);
        }
        return appDatabase;
    }


    public void insert(Clipboard clipboard, TaskRunner.Callback<Long> callable) {
        new TaskRunner().executeAsync(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return appDatabase.clipboardDao().insert(clipboard);
            }
        }, callable);
    }

    public void getClipboardList(TaskRunner.Callback<List<Clipboard>> callback) {
        new TaskRunner().executeAsync(new Callable<List<Clipboard>>() {
            @Override
            public List<Clipboard> call() throws Exception {
                return appDatabase.clipboardDao().getClipboardList();
            }
        }, callback);
    }

    public void delete(Clipboard clipboard) {
        new TaskRunner().executeAsync(new Callable<List<Clipboard>>() {
            @Override
            public List<Clipboard> call() throws Exception {
                appDatabase.clipboardDao().delete(clipboard);
                return null;
            }
        });
    }
}
