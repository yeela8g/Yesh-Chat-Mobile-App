package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebase extends FirebaseMessagingService {//allow getting notifications into the app

    public firebase() {
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) { //message - what the server send us
        int a = 1;
    }

}