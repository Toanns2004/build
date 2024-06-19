package com.example.roomdatabase2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase2.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "user.database";
    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getInstance(Context context){
        if (userDatabase==null){
            userDatabase = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return userDatabase;
    }

    public abstract UserDAO userDAO ();
}
