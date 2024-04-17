package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Repositories.ContactRepository;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.NewContact;
import com.example.myapplication.succeable.Successable;

import java.util.List;

public class ContactsVM extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contacts; //read-only, the ui fragmentList observes it

    public ContactsVM() {
        this.contactRepository = new ContactRepository();
        this.contacts = contactRepository.getContacts();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    } //for the recycler

    public void setSuccessable(Successable successable) {
        contactRepository.setSuccessable(successable);
    }

    public void add(NewContact contact) {
        contactRepository.add(contact);
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }
}
