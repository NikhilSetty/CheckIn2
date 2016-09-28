package com.mantra.checkin.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.OTPJsonkeys;
import com.mantra.checkin.Entities.JSONKEYS.UserInfoJSON;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.Utilities.OTPutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by adithyar on 9/26/2016.
 */
public class ChannelListForUserAdapter extends RecyclerView.Adapter<ChannelListForUserAdapter.CustomViewHolder> {

    private List<ChannelModel> channelModelList;
    private Context mcontext;
    public String json;
    public String publicjson;
    public static String TAG = "ChannelListAdapter";
    public String response;
    //public ChannelModel channelModel;
    public ChannelListForUserAdapter(Context context,List<ChannelModel> channelModelList) {
        this.channelModelList = channelModelList;
        this.mcontext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_single_row,null);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
         final ChannelModel channelModel = channelModelList.get(position);
        holder.tvtitle.setText(channelModel.getName());
        holder.tvdesctiption.setText("NEED CHANNEL DESC");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!channelModel.getPublic()){
                    if(!channelModel.getAuthenticated()) {
                        //show otp pop u edit text n authenticate
                        Log.d("inside adapter", "public");
                        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
                        View promptView = layoutInflater.inflate(R.layout.alert_dialog_for_otp, null);
                        final AlertDialog alertD = new AlertDialog.Builder(mcontext).create();
                        final EditText et_otp_alert = (EditText) promptView.findViewById(R.id.et_otp_alert);

                        final Button bt_otp_login_alert = (Button) promptView.findViewById(R.id.bt_otp_login_alert);

                        final Button bt_otp_request_alert = (Button) promptView.findViewById(R.id.bt_otp_request_alert);

                        bt_otp_login_alert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    json = OTPutil.create_otp_json(et_otp_alert.getText().toString(), channelModel.getChannelId());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                OTPutil.postOTPTokenandLogin(mcontext, json);
                            }
                        });

                        bt_otp_request_alert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //request_for_new_token();
                                try {
                                    json = OTPutil.create_resend_otp_json(channelModel.getChannelId());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                OTPutil.ResendOTPtoken(mcontext, json);
                            }
                        });
                        alertD.setView(promptView);

                        alertD.show();
                    }else{
                        Intent i = new Intent(mcontext,MainActivity.class);
                        mcontext.startActivity(i);
                    }


                }else {
                    try {
                         publicjson = OTPutil.create_login_json_public_channel(channelModel.getChannelId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogintoMainwithoutOTP(mcontext,publicjson);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != channelModelList ? channelModelList.size() : 0);
    }

    public void LogintoMainwithoutOTP(final Context context,final String json){

        AsyncTask<String, String, Boolean> logintopublicchannel = new AsyncTask<String, String, Boolean>() {
            String data = "";
            String response = "";
            public ProgressDialog mProgressDialog;


            @Override
            protected void onPreExecute() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage("Logging In");
                mProgressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mProgressDialog.dismiss();
                Log.d("OTP",aBoolean.toString());
                if (aBoolean) {
                    Log.d(TAG,"inside true");
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Failed to enter Channel", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                HttpPost httpPost = new HttpPost();
                try {
                    Log.d(TAG, json);
                    response = httpPost.post(APIUrls.BaseURl + APIUrls.RegisterToChannel, json);
                    ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
                    data = new JSONObject(response).getString("Data");
                    switch (responseStatusCodes) {
                        case Success:
                            ChannelModel model = ChannelModel.addChannelToDbAndGetModelFromJson(context,data);
                            SessionHelper.channelModelList.add(model);
                            Log.d(TAG, "returning true");
                            return true;
                        case Error:
                            Log.d(TAG, "returning false");
                            return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "returning false");
                return false;
            }
        };
        logintopublicchannel.execute("");
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvtitle;
        private TextView tvdesctiption;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.tvtitle = (TextView) itemView.findViewById(R.id.tvChannelTitle);
            this.tvdesctiption = (TextView) itemView.findViewById(R.id.tvChannelDescription);
        }

    }
}
