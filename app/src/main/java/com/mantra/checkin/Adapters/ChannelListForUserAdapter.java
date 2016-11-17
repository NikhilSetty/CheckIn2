package com.mantra.checkin.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.ChannelListForUser;
import com.mantra.checkin.DBHandlers.ChannelDbHandler;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.ProfileJsonKeys;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ProfileModel;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.Utilities.OTPutil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by adithyar on 9/26/2016.
 */
public class ChannelListForUserAdapter extends RecyclerView.Adapter<ChannelListForUserAdapter.CustomViewHolder> {

    private List<ChannelModel> channelModelList;
    private final Context mcontext;
    public String json;
    public String publicjson;
    public static String TAG = "ChannelListAdapter";
    public String response;
    public static HashMap<Integer,HashMap<String,String>> brandingmap;
    public static HashMap<String,String> brandingvalues;
    public String auth_private_channel="";

    //public ChannelModel channelModel;
    public ChannelListForUserAdapter(Context context, List<ChannelModel> channelModelList) {
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
        holder.itemView.setBackgroundColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(channelModel.getChannelId())).get(ChannelJsonKeys.ChannelPrimaryColor)));
        holder.tvtitle.setText(channelModel.getName());
        holder.tvtitle.setTextColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(channelModel.getChannelId())).get(ChannelJsonKeys.ChannelTertiaryColor)));
        holder.tvdesctiption.setTextColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(channelModel.getChannelId())).get(ChannelJsonKeys.ChannelTertiaryColor)));
        holder.tvdesctiption.setText(channelModel.getDescription());
//        try {
//            Picasso.with(mcontext).load(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(channelModel.getChannelId())).get(ChannelJsonKeys.ChannelIconUrl)).into(holder.logoimageview);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        //holder.tvtitle.setTextColor(Color.parseColor(channelModel.getTertiaryColor()));
        //holder.tvdesctiption.setTextColor(Color.parseColor(channelModel.getTertiaryColor()));
        holder.tvdesctiption.setText(channelModel.getDescription());

        if(!channelModel.getPublic()){
            holder.channellistimageview.setImageResource(R.drawable.actiondecryptedicon);
        }else if(channelModel.getPublic()){

        }
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
                                OTPutil.postOTPTokenandLogin(mcontext, json,channelModel.getChannelId());
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
                        ChannelModel dbmodel = ChannelDbHandler.get_model_from_channel_id(mcontext,channelModel.getChannelId());
                        if(dbmodel == null){
                            try {
                                auth_private_channel = OTPutil.create_login_json_public_channel(channelModel.getChannelId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            LogintoMainwithoutOTP(mcontext,auth_private_channel,channelModel.getChannelId());
                        }else {
                            // SessionHelper.channelModelList.add(dbmodel);
                            if(!ChannelListForUser.mIsFromMain) {
                                Intent i = new Intent(mcontext, MainActivity.class);
                                MainActivity.currentChannelId = channelModel.getChannelId();
                                mcontext.startActivity(i);
                            }else{
                                SessionHelper.channelModelList.add(dbmodel);
                            }
                            ((Activity) mcontext).finish();
                        }
                    }


                }else {
                    try {
                         publicjson = OTPutil.create_login_json_public_channel(channelModel.getChannelId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogintoMainwithoutOTP(mcontext,publicjson,channelModel.getChannelId());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != channelModelList ? channelModelList.size() : 0);
    }

    public void LogintoMainwithoutOTP(final Context context, final String publicjson, final String channelid){

        final AsyncTask<String, String, Boolean> logintopublicchannel = new AsyncTask<String, String, Boolean>() {
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
                    Log.d(TAG,"configuring profile");
                    ProfileModel.get_db_model_and_configure_profile(mcontext,channelid);
                    if(!ChannelListForUser.mIsFromMain) {
                        Intent i = new Intent(context, MainActivity.class);
                        MainActivity.currentChannelId = channelid;
                        context.startActivity(i);
                    }
                    ((Activity) mcontext).finish();
                } else {
                    Toast.makeText(context, "Failed to enter Channel", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                HttpPost httpPost = new HttpPost();
                try {
                    Log.d(TAG, publicjson);
                    response = httpPost.post(APIUrls.BaseURl + APIUrls.GETCHANNEL, publicjson);
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
        private ImageView channellistimageview;
       // private ImageView logoimageview;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.tvtitle = (TextView) itemView.findViewById(R.id.tvChannelTitle);
            this.tvdesctiption = (TextView) itemView.findViewById(R.id.tvChannelDescription);
            this.channellistimageview =(ImageView) itemView.findViewById(R.id.channellistlocksymbol);
            //this.logoimageview = (ImageView) itemView.findViewById(R.id.logo);
        }

    }
}
