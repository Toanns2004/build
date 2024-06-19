package com.example.roomdatabase2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabase2.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getListUser();



    @Query("SELECT * FROM user WHERE phone= :phone")
    List<User> checkData(String phone);


}
