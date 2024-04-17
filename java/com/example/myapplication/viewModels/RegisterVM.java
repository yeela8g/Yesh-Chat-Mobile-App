package com.example.myapplication.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.Repositories.RegisterRepository;
import com.example.myapplication.succeable.Successable;

public class RegisterVM extends ViewModel {
    private RegisterRepository registerRepository;
    public RegisterVM() {
        this.registerRepository = new RegisterRepository();
    }

    public void setSuccessable(Successable successable) {
        this.registerRepository.setSuccessable(successable);
    }
    public void registerUser(String username, String password, String displayName, String profilePic) {
        registerRepository.addUser(username, password, displayName, profilePic);
    }

}
