package com.example.myapplication.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Message;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private List<Message> messages;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;
        private final TextView time;

        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.messageContent);
            time = view.findViewById(R.id.messageTime);
        }
    }


    public MessageListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (messages != null) {
            if (messages.get(position).isSent()) {
                return 0; //from the user
            }
            return 1; //from the contact
        }
        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = this.inflater.inflate(R.layout.message_sent, viewGroup, false);
        } else {
            view = this.inflater.inflate(R.layout.message_recieved, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (this.messages != null) {
            viewHolder.content.setText(messages.get(position).getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date created = messages.get(position).getCreated();
            viewHolder.time.setText(sdf.format(created));
        }
    }


    @Override
    public int getItemCount() {
        if (this.messages == null)
            return 0;
        return this.messages.size();
    }


    public void setMessageList(List<Message> newMessages) {
        this.messages = newMessages;
        sortMessagesByCreatedTime(); // Sort the messages list by created time
        notifyDataSetChanged();
    }

    private void sortMessagesByCreatedTime() {
        if (messages != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(messages, Comparator.comparing(Message::getCreated));
            }
        }
    }
}
