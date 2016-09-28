package com.mantra.checkin.UiFragments.ChatRooms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantra.checkin.Entities.Models.ChatMessages;
import com.mantra.checkin.Entities.Models.UrlModel;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nravishankar on 9/28/2016.
 */
public class ChatMessagesListViewAdapter extends ArrayAdapter<ChatMessages> {
    private final Context context;
    private List<ChatMessages> values;

    public ChatMessagesListViewAdapter(Context context, int resource, List<ChatMessages> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_chat_item, parent, false);
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewChatMessageName);
        TextView textViewMessage = (TextView) rowView.findViewById(R.id.textViewChatMessage);
        TextView textViewTime = (TextView) rowView.findViewById(R.id.textViewChatMessageTime);

        if(values.get(position).IsAdminMessage) {
            textViewName.setText("Admin");
            textViewName.setTextColor(Color.RED);
        }else if(values.get(position).SenderId.equals(SessionHelper.user.CheckInServerUserId)){
            textViewName.setText("You");
        } else {
            textViewName.setText(values.get(position).SenderName);
        }
        textViewMessage.setText(values.get(position).Message);

        textViewTime.setText(new SimpleDateFormat("HH:mm").format(values.get(position).TimeStamp));

        return rowView;
    }
}
