package com.example.myapplication.API;

import androidx.annotation.NonNull;

import com.example.myapplication.models.Login;
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

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Successable successable;

    public LoginAPI(Successable s) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Info.baseUrlServer + "/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            webServiceAPI = retrofit.create(WebServiceAPI.class);
            successable = s;
    }

    public void loginToServer(String username, String password) {
        Call<String> call = webServiceAPI.logIn(new Login(username, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200 && response.body() != null) {
                    String token = response.body();
                    Info.loggerUserToken = token;
                    Info.isLogged = true;
                    Info.loggedUser = username;
                    successable.onSuccess();
                } else {
                    if (response.body() != null) {} //redundant?
                    Info.loggedUser = null;
                    Info.loggerUserToken = null;
                    successable.onFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Info.loggedUser = null;
                Info.loggerUserToken = null;
                successable.onFail();
            }

        });
    }
}
