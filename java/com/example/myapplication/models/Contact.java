package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey()
    @NonNull
    private String id;
    private UserWithoutPass user;
    private lastMessage lastMessage;


    public Contact(String id, UserWithoutPass user, lastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    // Getters and setters for the properties

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserWithoutPass getUser() {
        return user;
    }

    public void setUser(UserWithoutPass user) {
        this.user = user;
    }

    public lastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(lastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String toString() {
        String content = "";
        if(lastMessage != null){content = lastMessage.getContent();};
        return "User{" +
                "id='" + id + '\'' +
                ", user ='" + user.getUsername() + '\'' +
                ", lastMessage..'" + content
        + '\''+'}' ;
    }
}
