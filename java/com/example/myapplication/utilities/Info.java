package com.example.myapplication.utilities;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import com.example.myapplication.room.UserDB;

public class Info extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static String loggedUser;
    public static String loggerUserToken;
    public static boolean isLogged;
    public static UserDB usersDB;
    public static String baseUrlServer;
    public static String contactId;


    @Override
    public void onCreate() {
        super.onCreate();
        baseUrlServer ="https://social-chat-app-21.onrender.com"; //locally: "http://10.0.2.2:5000";
        context = getApplicationContext();
        loggedUser = null;
        loggerUserToken = null;
        isLogged = false;
        usersDB = Room.databaseBuilder(getApplicationContext(), UserDB.class, "user")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

    }

    public static void resetUserInformation() {
        loggedUser = null;
        loggerUserToken = null;
        isLogged = false;
    }
}
