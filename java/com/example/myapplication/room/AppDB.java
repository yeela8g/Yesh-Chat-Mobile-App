package com.example.myapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.models.Contact;
import com.example.myapplication.models.Message;

@Database(entities = {Contact.class, Message.class}, version = 9, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDAO contactDao();
    public abstract MessageDAO messageDao();
}
