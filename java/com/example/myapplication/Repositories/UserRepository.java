package com.example.myapplication.Repositories;

import com.example.myapplication.API.UserAPI;
import com.example.myapplication.models.User;

public class UserRepository {
    private UserAPI userAPI;
    public UserRepository() {
        userAPI = new UserAPI();
    }

    public User getUser(String username) {
        return userAPI.getUser(username);
    }



}
