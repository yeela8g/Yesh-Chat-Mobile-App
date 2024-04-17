package com.example.myapplication.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.API.MessageAPI;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.SendMsg;
import com.example.myapplication.room.AppDB;
import com.example.myapplication.room.ContactDAO;
import com.example.myapplication.room.MessageDAO;
import com.example.myapplication.utilities.Info;

import java.util.List;

public class MessageRepository {
    private MessageAPI messageApi;
    private ContactDAO contactDao;
    private MessageDAO messageDao;
    private MessageListData messagesListData;

    public MessageRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.messageDao = appDB.messageDao();
        this.contactDao = appDB.contactDao();
        this.messageApi = new MessageAPI(this.messageDao);
        this.messagesListData = new MessageListData();
    }

    public LiveData<List<Message>> get() {
        return messagesListData;
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(messageDao.getChatMessages(Info.contactId));
        }

        @Override
        protected void onActive() {
            super.onActive();
            setMessagesListDataWithApiServerMessages();
        }
    }

    public void setMessagesListDataWithApiServerMessages() {
        this.messageApi.getAllMessages(this.messagesListData,
                "Bearer " + Info.loggerUserToken, Info.contactId);
    }

    public void add(SendMsg message) {
        messageApi.add(messagesListData , message);
    }

}
