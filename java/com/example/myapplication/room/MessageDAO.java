package com.example.myapplication.room;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.models.Message;

import java.util.List;

@Dao
public interface MessageDAO {

    @Insert
    void insert(Message... Messages);

    @Query("DELETE FROM messages")
    void clear();

    @Query("SELECT * FROM messages WHERE ChatId = :contactId")
    List<Message> getChatMessages(String contactId);


}
