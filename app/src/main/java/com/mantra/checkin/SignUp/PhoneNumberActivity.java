package com.mantra.checkin.SignUp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Set;

public class PhoneNumberActivity extends AppCompatActivity {

    private final String TAG = "PhoneNumberActivity";

    Spinner countrySelectorSpinner;
    Button buttonContinue;
    EditText editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        /*countrySelectorSpinner = (Spinner) findViewById(R.id.spinnerCountryCode);
        ArrayAdapter<String> countryCodesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LoadAllCountryCodes());
        countrySelectorSpinner.setAdapter(countryCodesAdapter);*/

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePhoneNumberAndContinue();
            }
        });
    }

    private void SavePhoneNumberAndContinue() {
        try{
            // Defensive
            final String phoneNumber = editTextPhoneNumber.getText().toString();
            if(phoneNumber.isEmpty() || phoneNumber.length() < 10){
                Toast.makeText(getApplicationContext(), "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save into Db
            UserInfoDBHandler.updatePhoneNumberForUser(getApplicationContext(), phoneNumber);
            SessionHelper.user.setPhoneNumber(phoneNumber);

            // Make Http Call to server
            AsyncTask<String, Boolean, Boolean> updatePhoneNumber = new AsyncTask<String, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(String[] params) {
                    if(!SessionHelper.user.getUserID().isEmpty()) {
                        try {
                            JSONObject json = new JSONObject();

                            json.put("CheckInServerUserId", SessionHelper.user.getCheckInServerUserId());
                            json.put("PhoneNumber", phoneNumber);
                            String jsonEntity = json.toString();
                            String response = new HttpPost().post("serverUrl", jsonEntity);

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
                    }
                    Log.e(TAG, "User not logged in yet or token is empty.");
                    return false;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                }
            };
            updatePhoneNumber.execute("");

            // Launch Next Activity
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private String[] LoadAllCountryCodes(){
        Set<String> set = PhoneNumberUtil.getInstance().getSupportedRegions();

        String[] arr = set.toArray(new String[set.size()]);

        for (int i = 0; i < set.size(); i++) {
            Locale locale = new Locale("en", arr[i]);
        }

        return arr;
    }
}
