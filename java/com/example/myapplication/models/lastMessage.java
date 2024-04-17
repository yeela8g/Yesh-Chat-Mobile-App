package com.example.myapplication.models;

import java.util.Date;

public class lastMessage {
    private int id;
    private Date created;
    private String content;


    public lastMessage(int id, Date created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }

    // Getters and setters for the properties

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
