package com.example.bhuhospital.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoInterface {

    @Insert
    void Insert(appoinment_entity note);

    @Delete
    void Delete(appoinment_entity note);

    @Update
    void Update(appoinment_entity note);

    @Query("SELECT * FROM Appoinment WHERE isPending=0")
    LiveData<List<appoinment_entity>> getAllNonPendingNotes();

    @Query("SELECT * FROM Appoinment WHERE isPending=1")
    LiveData<List<appoinment_entity>> getAllPendingNotes();


    @Query("SELECT * FROM Appoinment")
    LiveData<List<appoinment_entity>>getAllNotes();
    @Query("DELETE FROM Appoinment")
    void DeleteAllNotes();




}
