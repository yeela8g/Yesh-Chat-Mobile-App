package com.example.myapplication.service;

import com.example.myapplication.models.AddMessageResponse;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.Login;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.NewContact;
import com.example.myapplication.models.SendMsg;
import com.example.myapplication.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("/api/Users")
    Call<Void> registerUser(@Body User user);

    @POST("/api/Tokens")
    Call<String> logIn(@Body Login login);

    @GET("/api/Chats")
    Call<List<Contact>> getAllContacts(@Header("authorization") String auth);

    @POST("/api/Chats")
    Call<Contact> addChat(@Header("authorization") String auth, @Body NewContact newContact);

    @GET("/api/Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") String chatId, @Header("authorization") String auth);

    @POST("/api/Chats/{id}/Messages")
    Call<AddMessageResponse> postMessage(@Path("id") String id, @Body SendMsg message,
                                         @Header("authorization") String auth);


    @DELETE("/api/Chats/{id}")
    Call<Void> deleteContact(@Header("Authorization") String token, @Path("id") String contactId);

}
