package com.example.myapplication.room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.models.User;

@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class UserDB extends RoomDatabase { //abstract class represent the user db
    public abstract UserDAO getUserDAO(); //declaration of method that returns an instance of the UserDAO interface.
}
