package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Repositories.MessageRepository;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.SendMsg;

import java.util.List;

public class MessagesVM extends ViewModel {
    private MessageRepository messageRepository;
    private LiveData<List<Message>> messages;

    public MessagesVM() {
        this.messageRepository = new MessageRepository();
        this.messages = messageRepository.get();
    }
    public LiveData<List<Message>> get() {
        return messages;
    } //return messagesList to adapter

    public void add(SendMsg message) {
        messageRepository.add(message);
    }

}
