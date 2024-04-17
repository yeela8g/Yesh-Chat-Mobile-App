package com.example.myapplication.API;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.Contact;
import com.example.myapplication.models.NewContact;
import com.example.myapplication.room.ContactDAO;
import com.example.myapplication.service.WebServiceAPI;
import com.example.myapplication.succeable.Successable;
import com.example.myapplication.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {
    private ContactDAO contactDao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private Successable successable;


    public ContactAPI(ContactDAO contactDao) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS").setLenient().create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Info.baseUrlServer +
                         "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
        this.contactDao = contactDao;
    }

    public void setSuccessable(Successable successable) {
        this.successable = successable;
    }


    public void getAllContacts(MutableLiveData<List<Contact>> contacts, String token) { //contacts=contactListData[liveData List<Contact>] of repository
        Call<List<Contact>> call = webServiceAPI.getAllContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call,
                                   @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    contactDao.clear(); //delete all contacts records
                    if (response.body() == null) {
                        return;
                    }
                    // add the all contacts to the dao
                    for (Contact contact : response.body()) { //update the local db with the the server contacts
                        contactDao.insert(contact);
                    }
                    contacts.postValue(response.body()); //push to the repository contactListData the full contacts items
                }).start();

            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Log.i("onFailure", "onfailure", t);
            }
        });
    }

    public void addContact(NewContact contact, String username, String token,
                           MutableLiveData<List<Contact>> contacts) {
        Call<Contact> call = webServiceAPI.addChat(token, contact);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(@NonNull Call<Contact> call, @NonNull Response<Contact> response) {
                if (response.isSuccessful()) {
                    successable.onSuccess();
                    Contact res_contact = response.body();
                    contactDao.insert(res_contact);
                    List<Contact> newContacts = contacts.getValue();
                    newContacts.add(res_contact);
                    contacts.postValue(newContacts);

                    // send after invitation, post for the contact
//                    addToMyServer(contact, token, contacts);

                } else if (successable != null) {
                    successable.onFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Contact> call, @NonNull Throwable t) {
                if (successable != null) {
                    successable.onFail();
                }
            }
        });
    }

    public void delete(Contact contact) {

        // Delete from the server
        String contactId = contact.getId();
        String token = "Bearer " + Info.loggerUserToken;
        Call<Void> call = webServiceAPI.deleteContact(token, contactId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 204) {
                    Log.e("delete contact" , "succeeded");
                    // Handle successful deletion
                } else {
                    // Handle unsuccessful deletion
                    Log.e("delete contect" , "failed");

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Log.e("delete contect" , "failed2");
            }
        });
    }

}