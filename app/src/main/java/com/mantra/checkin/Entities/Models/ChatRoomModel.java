package com.mantra.checkin.Entities.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChatRoomModel {
    public String ChatRoomId = "";
    public String ChatRoomName = "";

    public List<ChatMessages> messages = new ArrayList<>();
}
