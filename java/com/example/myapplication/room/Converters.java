package com.example.myapplication.room;

import androidx.room.TypeConverter;

import com.example.myapplication.models.Message;
import com.example.myapplication.models.UserUsername;
import com.example.myapplication.models.UserWithoutPass;
import com.example.myapplication.models.lastMessage;
import com.google.gson.Gson;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static UserWithoutPass fromUserString(String value) {
        return new Gson().fromJson(value, UserWithoutPass.class);
    }

    @TypeConverter
    public static String toUserString(UserWithoutPass user) {
        return new Gson().toJson(user);
    }

    @TypeConverter
    public static Message fromMessageString(String value) {
        return new Gson().fromJson(value, Message.class);
    }

    @TypeConverter
    public static String toMessageString(Message message) {
        return new Gson().toJson(message);
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
    @TypeConverter
    public static lastMessage fromString(String value) {
        return new Gson().fromJson(value, lastMessage.class);
    }

    @TypeConverter
    public static String toString(lastMessage lastMessage) {
        return new Gson().toJson(lastMessage);
    }

    @TypeConverter
    public static UserUsername fromUserUsernameString(String value) {
        return new Gson().fromJson(value, UserUsername.class);
    }

    @TypeConverter
    public static String toUserUsernameString(UserUsername user) {
        return new Gson().toJson(user);
    }
}

