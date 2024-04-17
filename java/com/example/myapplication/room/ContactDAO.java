package com.example.myapplication.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.models.Contact;

import java.util.List;
@Dao
public interface ContactDAO {
    @Insert
    void insert(Contact... Contacts);

    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();

    @Query("DELETE FROM contacts")
    void clear();

    @Delete
    void delete(Contact... contacts);

}
