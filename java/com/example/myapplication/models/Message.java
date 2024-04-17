package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.utilities.Info;

import java.util.Date;

@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int msgId;

    String chatId;
    private int id;//(msg)
    private Date created;
    private UserUsername sender;
    private String content;


    public Message(int id, Date created, UserUsername sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    // Getters and setters for the properties
    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
    public void setChatId(String chatId){
        this.chatId = chatId;
    }

    public String getChatId(){
        return this.chatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserUsername getSender() {
        return sender;
    }

    public void setSender(UserUsername sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public boolean isSent(){
        if(sender.getUsername().equals(Info.loggedUser)){
            return true;
        }else{
            return false;
        }
    }

    public String toString() {
        return "message{" +
                "sender='" + getSender().getUsername() + '\'' +
                ", content ='" + getContent() +'}' ;
    }
}
