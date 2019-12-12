package com.droidhelios.clipboard.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;



@Dao
public interface ClipboardDao {

    @Query("SELECT * FROM clipboard")
    LiveData<List<Clipboard>> getAll();

    @Query("SELECT * FROM clipboard order by id desc")
    List<Clipboard> getClipboardList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Clipboard task);

    @Delete
    void delete(Clipboard task);

}