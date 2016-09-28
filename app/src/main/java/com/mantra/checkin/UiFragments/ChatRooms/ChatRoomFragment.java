package com.mantra.checkin.UiFragments.ChatRooms;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.DBHandlers.ChatMessagesDbHelper;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.SendMessageJsonKeys;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChatMessages;
import com.mantra.checkin.Entities.Models.ChatRoomModel;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.UiFragments.Applications.ApplicationListViewAdapter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatRoomFragment extends Fragment {

    private final static String TAG = "ChatRoomFragment";
    ListView mListView;
    EditText editTextMessage;
    Button buttonSend;

    ChatRoomModel currentChatRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_chat_room, container, false);
        mListView = (ListView) layout.findViewById(R.id.list_view_messages);
        editTextMessage = (EditText) layout.findViewById(R.id.inputMsg);
        buttonSend = (Button) layout.findViewById(R.id.btnSend);

        ChannelModel model = new ChannelModel();
        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            if(SessionHelper.channelModelList.get(i).ChannelId.equals(MainActivity.currentChannelId)){
                model = SessionHelper.channelModelList.get(i);
                break;
            }
        }

        currentChatRoom = model.ChatRooms.get(0);
        PopulateListView();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
        return layout;
    }

    private void SendMessage() {
        try{

            String message = editTextMessage.getText().toString();
            if(message.trim().isEmpty()){
                Toast.makeText(getActivity(), "Please Enter a Message to send!",Toast.LENGTH_SHORT).show();
                return;
            }

            ChatMessages model = new ChatMessages();
            model.ChatRoomId = currentChatRoom.ChatRoomId;
            model.IsAdminMessage = false;
            model.SenderId = SessionHelper.user.CheckInServerUserId;
            model.SenderName = SessionHelper.user.FirstName;
            model.IsImage = false;
            model.ImageUrl = "";
            model.Message = message;
            model.TimeStamp = new Date();

            List<ChatMessages> list = new ArrayList<>();
            list.add(model);

            Boolean isSuccess = ChatMessagesDbHelper.AddChatMessagesIntoDb(getActivity(), list);

            if(isSuccess) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(SendMessageJsonKeys.ChatRoomId, Integer.valueOf(model.ChatRoomId));
                jsonObject.put(SendMessageJsonKeys.UserName, model.SenderName);
                jsonObject.put(SendMessageJsonKeys.UserId, Integer.valueOf(model.SenderId));
                jsonObject.put(SendMessageJsonKeys.ImageArray, "");
                jsonObject.put(SendMessageJsonKeys.IsAdminMessage, false);
                jsonObject.put(SendMessageJsonKeys.IsImage, false);
                jsonObject.put(SendMessageJsonKeys.Message, message);
                jsonObject.put(SendMessageJsonKeys.TimeOfGeneration, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(model.TimeStamp));

                final String entity = jsonObject.toString();

                AsyncTask<String, Boolean, Boolean> sendChatToServer = new AsyncTask<String, Boolean, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String[] params) {
                        try {
                            String response = new HttpPost().post(APIUrls.BaseURl + APIUrls.SEND_MESSAGE, entity);

                            ResponseStatusCodes statusCodes = Utility.getResponseStatus(response);

                            switch (statusCodes) {
                                case Success:
                                    return true;
                                case Error:
                                    return false;
                            }

                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                    }
                };
                sendChatToServer.execute("");
                PopulateListView();
                editTextMessage.setText("");
            }


        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void PopulateListView() {
        currentChatRoom.messages = ChatMessagesDbHelper.getAllMessagesForChatRoomId(getActivity(), currentChatRoom.ChatRoomId);
        ChatMessagesListViewAdapter adapter = new ChatMessagesListViewAdapter(getActivity(), 0, currentChatRoom.messages);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
