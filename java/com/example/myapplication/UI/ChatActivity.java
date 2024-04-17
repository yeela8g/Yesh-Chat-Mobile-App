package com.example.myapplication.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MessageListAdapter;
import com.example.myapplication.models.SendMsg;
import com.example.myapplication.utilities.Info;
import com.example.myapplication.viewModels.MessagesVM;

public class ChatActivity extends AppCompatActivity {
    MessagesVM messagesViewModel;
    MessageListAdapter adapter;
    Bitmap bitmap;
    private static final String THEME_PREFS_KEY = "theme_prefs";
    private static final String SELECTED_THEME_KEY = "selected_theme";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the selected theme from SharedPreferences
        sharedPreferences = getSharedPreferences(THEME_PREFS_KEY, MODE_PRIVATE);
        int selectedTheme = sharedPreferences.getInt(SELECTED_THEME_KEY, R.style.LightTheme_MyApplication);
        setTheme(selectedTheme);

        setContentView(R.layout.activity_chat);

        messagesViewModel = new ViewModelProvider(this).get(MessagesVM.class);

        bitmap = null;
        setNicknameHeader();
        setPictureHeader();
        setMessageList(); //display chat messages
        setMessageBar(); //send new message
    }


    private void setNicknameHeader() {
        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        TextView contactNicknameTV = findViewById(R.id.ChatDisplayName);
        contactNicknameTV.setText(props.get("contactDisplayName").toString());
    }

    private void setPictureHeader() {
        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        // check if this contact is in our room db as a user and get his picture
        ImageView contactPictureTV = findViewById(R.id.ChatProfilePic);
        String picContact = props.get("contactProfilePic").toString();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO && picContact != "") {
            byte[] profileImage = Base64.decode(picContact, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            contactPictureTV.setImageBitmap(bitmap);
        }
    }

    private void setMessageList() {
        RecyclerView messagesListRV = findViewById(R.id.messagesRecyclerView);
        messagesListRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(Info.context);

        messagesViewModel.get().observe(this, messages -> {
            adapter.setMessageList(messages);
            messagesListRV.scrollToPosition(messages.size()-1);
        });

        messagesListRV.setAdapter(adapter);
    }

    private void setMessageBar() { //send message functionality
        Button sendMsgButton = findViewById(R.id.sendMsgButton);
        sendMsgButton.setOnClickListener(v -> {
            EditText messageET = findViewById(R.id.newMessageInput);
            String content = messageET.getText().toString();
            if (content.equals("")) {
                Toast.makeText(this, "There is nothing to send", Toast.LENGTH_SHORT).show();
                return;
            }

            // set the textbox to be null
            messageET.setText("");
            SendMsg message = new SendMsg(content);
            messagesViewModel.add(message);
        });
    }
}