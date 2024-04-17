package com.example.myapplication.models;

import java.util.Date;

public class AddMessageResponse {
    int id; //msgId
    Date created;
    UserWithoutPass sender;
    String content;

    public AddMessageResponse(int id, Date created, UserWithoutPass sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
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

    public UserWithoutPass getSender() {
        return sender;
    }

    public void setSender(UserWithoutPass sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
