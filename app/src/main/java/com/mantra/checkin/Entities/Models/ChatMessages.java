package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.ChatMessagesJsonKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChatMessages {
    public String MessageId = "";
    public String ChatRoomId = "";
    public Boolean IsImage = false;
    public Boolean IsAdminMessage = false;
    public String SenderId = "";
    public String Message = "";
    public String ImageUrl = "";
    public Date TimeStamp = new Date();
    public String SenderName = "";

    public static List<ChatMessages> getMessageFromJsonObject(JSONArray chatRoomMessages) throws Exception {
        List<ChatMessages> list = new ArrayList<>();
        for(int i = 0; i < chatRoomMessages.length(); i++){
            ChatMessages model = new ChatMessages();
            JSONObject modelObject = new JSONObject(chatRoomMessages.get(i).toString());

            model.ChatRoomId = Integer.toString(modelObject.getInt(ChatMessagesJsonKeys.ChatMessageRoomId));
            model.MessageId = Integer.toString(modelObject.getInt(ChatMessagesJsonKeys.ChatMessageId));
            model.IsImage = modelObject.getBoolean(ChatMessagesJsonKeys.ChatMessageIsImage);
            model.IsAdminMessage = modelObject.getBoolean(ChatMessagesJsonKeys.ChatMessageIsAdminMessage);
            model.SenderId = Integer.toString(modelObject.getInt(ChatMessagesJsonKeys.ChatMessageUserId));
            if(!model.IsImage) {
                model.Message = modelObject.getString(ChatMessagesJsonKeys.ChatMessageChatMessage);
            }else{
                model.ImageUrl = modelObject.getString(ChatMessagesJsonKeys.ChatMessageChatMessage);
            }

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            model.TimeStamp = (Date) formatter.parse(modelObject.getString(ChatMessagesJsonKeys.ChatMessageTimeOfGeneration));

            model.SenderName = modelObject.getString(ChatMessagesJsonKeys.ChatMessageSenderName);

            list.add(model);
        }

        return list;
    }
}
