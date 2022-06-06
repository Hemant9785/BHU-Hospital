package com.example.bhuhospital.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {appoinment_entity.class},version=1)
public  abstract  class room_database extends RoomDatabase {
    private static room_database instance;

    public abstract DaoInterface daoInterface();

    public static synchronized  room_database getInstance(Context ctx)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),room_database.class,"room_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
