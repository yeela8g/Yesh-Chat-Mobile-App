package com.example.myapplication.API;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.myapplication.models.User;
import com.example.myapplication.room.UserDAO;
import com.example.myapplication.service.WebServiceAPI;
import com.example.myapplication.succeable.Successable;
import com.example.myapplication.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private UserDAO userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Successable successable;


    public UserAPI() {
        Gson gson = new GsonBuilder().setLenient().create();
        userDao = Info.usersDB.getUserDAO();
        retrofit = new Retrofit.Builder().baseUrl(Info.baseUrlServer + "/") //set url
                .addConverterFactory(GsonConverterFactory.create(gson)) //factory for de/serialization JSONs
                .build(); //create retrofit instance
        webServiceAPI = retrofit.create(WebServiceAPI.class); //create server api instance with HTTP requests.

    }

    public void setSuccessable(Successable successable) {
        this.successable = successable;
    }

    //register in service + local db
    public void addUser(String username, String password, String displayName, String profilePic) {
        User user = new User(username, password, displayName, profilePic);
        Call<Void> call = webServiceAPI.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    userDao.insert(user);
                    successable.onSuccess();
                } else {
                    successable.onFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                successable.onFail();
            }
        });
    }

    public User getUser(String username) {
        RoomUsers roomUsers = new RoomUsers(userDao, username);
        User user;
        try {
            user = roomUsers.execute().get(); //execute:start the inBackground method. get: like await- wait for the response and don't continue until then.
        } catch (Exception exception) {
            user = null;
        }
        return user;
    }

    // get user from room async
    private class RoomUsers extends AsyncTask<Void, Void, User> {
        private UserDAO userDao;
        private String username;

        public RoomUsers(UserDAO userDao, String username) {
            this.userDao = userDao;
            this.username = username;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getUserByUsername(username);
        }
    }


}
