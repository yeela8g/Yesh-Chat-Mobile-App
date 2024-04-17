package com.example.myapplication.Repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.API.ContactAPI;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.NewContact;
import com.example.myapplication.room.AppDB;
import com.example.myapplication.room.ContactDAO;
import com.example.myapplication.succeable.Successable;
import com.example.myapplication.utilities.Info;

import java.util.List;

public class ContactRepository {
    private ContactAPI contactApi;
    private ContactDAO contactDao;
    private ContactListData contactListData;


    public ContactRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser) //create new db from type appDB named ${username}
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.contactDao = appDB.contactDao(); //get the contacts dao instance
        this.contactListData = new ContactListData();
        this.contactApi = new ContactAPI(this.contactDao);
    }


    class ContactListData extends MutableLiveData<List<Contact>> { //mutable
        public ContactListData() {
            super();
            setValue(contactDao.getAllContacts()); //the repository automatically will contain the local data of the contacts.
        }

        @Override
        protected void onActive() { //when there are listeners (if fragment is stopped\destoryed the listeners'll stop observe
            super.onActive();
            setContactListDataWithServerAPIContacts();// load contacts from server API
        }
    }


    public void setContactListDataWithServerAPIContacts() { //when the contactsListData will be observed this method will be called and will get the contacts from the service
        this.contactApi.getAllContacts(this.contactListData,
                "Bearer " + Info.loggerUserToken);
    }


    public void setSuccessable(Successable successable) {
        contactApi.setSuccessable(successable);
    }


    public MutableLiveData<List<Contact>> getContacts() {
        return contactListData;
    }

    public void add(NewContact contact) {
        this.contactApi.addContact(contact, Info.loggedUser,
                "Bearer " + Info.loggerUserToken, this.contactListData);
    }

    public void delete(Contact contact) {
        contactDao.delete(contact);
        contactListData.setValue(contactDao.getAllContacts());
        contactApi.delete(contact);
    }

}
