package com.example.myapplication.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.Repositories.UserRepository;
import com.example.myapplication.models.User;

public class UserVM extends ViewModel {
    private UserRepository userRepository;

    public UserVM() {
        this.userRepository = new UserRepository();
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }

}
