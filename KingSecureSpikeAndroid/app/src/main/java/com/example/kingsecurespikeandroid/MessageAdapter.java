package com.example.kingsecurespikeandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{

        TextView messageText;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.sentTxt);
        }
    }

    private class ReceiveMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;

        public ReceiveMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        JSONObject message = messages.get(position);

        try {
            if(message.getBoolean("isSent")){
                if (message.has("message")){
                    return TYPE_MESSAGE_SENT;
                }
            } else {
                if (message.has("message")) {
                    return TYPE_MESSAGE_RECEIVED;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType){
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_received_message, parent, false);
                return new ReceiveMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message = messages.get(position);

        try {
            if(message.getBoolean("isSent")){
               if(message.has("message")){
                   SentMessageHolder messageHolder = (SentMessageHolder) holder;
                   messageHolder.messageText.setText(message.getString("message"));
               }
            } else{
                if(message.has("message")){
                    ReceiveMessageHolder messageHolder = (ReceiveMessageHolder) holder;
                    messageHolder.nameTxt.setText(message.getString("name"));
                    messageHolder.messageTxt.setText(message.getString("message"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(JSONObject jsonObject){
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
