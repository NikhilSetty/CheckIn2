package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.ChatRoomJsonKeys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChatRoomModel {
    public String ChatRoomId = "";
    public String ChatRoomName = "";

    public List<ChatMessages> messages = new ArrayList<>();

    public static List<ChatRoomModel> getChatRoomListFromJsonObject(JSONArray chatRoomArray) throws Exception {
        List<ChatRoomModel> list = new ArrayList<>();
        for(int i = 0; i < chatRoomArray.length(); i++){
            ChatRoomModel model = new ChatRoomModel();
            JSONObject modelObject = new JSONObject(chatRoomArray.get(i).toString());

            model.ChatRoomId = Integer.toString(modelObject.getInt(ChatRoomJsonKeys.ChatRoomId));
            model.ChatRoomName = modelObject.getString(ChatRoomJsonKeys.ChatRoomName);

            model.messages = ChatMessages.getMessageFromJsonObject(modelObject.getJSONArray(ChatRoomJsonKeys.ChatRoomMessages));

            list.add(model);
        }
        return list;
    }
}
