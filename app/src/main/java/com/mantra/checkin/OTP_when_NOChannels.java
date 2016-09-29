package com.mantra.checkin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.OTPJsonkeys;
import com.mantra.checkin.Entities.JSONKEYS.UserInfoJSON;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.Utilities.OTPutil;

import org.json.JSONException;
import org.json.JSONObject;

public class OTP_when_NOChannels extends AppCompatActivity {
    EditText et_otp;
    Button bt_otp_login, bt_request_token;
    public String response = "";
    public String json = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_channels_for_user);
        et_otp = (EditText) findViewById(R.id.et_otp);
        bt_otp_login = (Button) findViewById(R.id.bt_otp_login);
        bt_request_token = (Button) findViewById(R.id.bt_otp_request);

        bt_otp_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    json = OTPutil.create_otp_json(et_otp.getText().toString(),"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OTPutil.postOTPTokenandLogin(OTP_when_NOChannels.this, json,"");
            }
        });

        bt_request_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Request for new Token here
                try {
                    json = OTPutil.create_resend_otp_json("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OTPutil.ResendOTPtoken(OTP_when_NOChannels.this, json);
            }
        });
    }
}