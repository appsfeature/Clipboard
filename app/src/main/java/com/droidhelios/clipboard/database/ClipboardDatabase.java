package com.droidhelios.clipboard.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Clipboard.class}, version = 1, exportSchema = false)
public abstract class ClipboardDatabase extends RoomDatabase {

    private static final String DB_NAME = "app-db";

    public abstract ClipboardDao clipboardDao();

    public static ClipboardDatabase create(Context context) {
        return Room.databaseBuilder(context, ClipboardDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}