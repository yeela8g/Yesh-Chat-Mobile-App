package com.example.myapplication.Repositories;
import com.example.myapplication.API.UserAPI;
import com.example.myapplication.succeable.Successable;

public class RegisterRepository {
    private UserAPI userAPI;
    public RegisterRepository(){
        this.userAPI = new UserAPI();
    }

    public void setSuccessable(Successable successable) {
        this.userAPI.setSuccessable(successable);
    }
    public void addUser(String username, String password, String displayName, String profilePic) {
        userAPI.addUser(username, password, displayName, profilePic);
    }

}


