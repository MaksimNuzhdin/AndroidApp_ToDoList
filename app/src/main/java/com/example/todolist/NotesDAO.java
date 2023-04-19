package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDAO {
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();//return экземпляр класса
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(Note note);//Return bool value
    @Query("DELETE FROM notes WHERE id=:id")
    Completable remove(int id);
}
