package com.mantra.checkin.SignUp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mantra.checkin.DBHandlers.SettingsInfoDBHandler;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.SettingsConstants;
import com.mantra.checkin.FCM.MyFireBaseInstanceIdService;
import com.mantra.checkin.Entities.JSONKEYS.UserInfoJSON;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.Entities.Models.SettingsInfo;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    public static String response = "";

    GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    SignInButton mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("360697764233-lukurm2i9li3o4atg0mn01mhaon1u6le.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(v);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection to Google failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        String json = "";

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                UserInfo userinfo = new UserInfo();
                try {
                    userinfo.setUserEmail(acct.getEmail());
                    userinfo.setUserName(acct.getDisplayName());
                    userinfo.setFirstName(acct.getGivenName());
                    userinfo.setLastName(acct.getFamilyName());
                    userinfo.setUserID(acct.getId());
                    UserInfoDBHandler.InsertUserDetails(getApplicationContext(), userinfo);
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //check if this is needed here or somewhere else
                SettingsInfo settingsInfo = new SettingsInfo();
                settingsInfo.Key = SettingsConstants.LoginStatus;
                settingsInfo.Value = "true";
                SettingsInfoDBHandler.InsertSettingsInfo(getApplicationContext(),settingsInfo);
                SessionHelper.loginstatus = true;

                UserInfo db_user_model = new UserInfo();
                db_user_model = UserInfoDBHandler.FetchCurrentUserDetails(getApplicationContext());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(UserInfoJSON.FIRSTNAME, db_user_model.getFirstName());
                    jsonObject.put(UserInfoJSON.LASTNAME, db_user_model.getLastName());
                    jsonObject.put(UserInfoJSON.USERNAME, db_user_model.getUserName());
                    jsonObject.put(UserInfoJSON.USEREMAIL, db_user_model.getUserEmail());
                    jsonObject.put(UserInfoJSON.USERID, db_user_model.getUserID());
                    jsonObject.put(UserInfoJSON.USER_PHONE_NUMBER, db_user_model.getPhoneNumber());
                    jsonObject.put(UserInfoJSON.USERPHOTO, "dummy url");
                    json = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SendUserDetailsToServer(getApplicationContext(),json);
            }
            else {
                //Exception to be raised here
                Toast.makeText(getApplicationContext(), "Account details are null", Toast.LENGTH_LONG).show();
            }
        }else {
            // Signed out, show unauthenticated UI.
        }

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.singing_in));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    // Sign In method for Google OAuth
    public void SignIn(View v){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

        public void SendUserDetailsToServer(final Context context,final String json) {
          AsyncTask<String,String,String> PostServerDetails = new AsyncTask<String,String,String>(){
              @Override
              protected void onPreExecute() {
                  mProgressDialog = new ProgressDialog(LoginActivity.this);
                  mProgressDialog.setMessage(getString(R.string.Fetching_details));
                  mProgressDialog.show();
                  super.onPreExecute();
              }

              @Override
              protected void onPostExecute(String s) {
                  super.onPostExecute(s);
                  mProgressDialog.dismiss();
                  if (s == null){
                      Toast.makeText(LoginActivity.this,"Unable to Reach our servers Please try again later",Toast.LENGTH_LONG).show();
                  }else {
                      MyFireBaseInstanceIdService.sendRegistrationToServer(LoginActivity.this);
                      Intent i = new Intent(LoginActivity.this, PhoneNumberActivity.class);
                      startActivity(i);
                      finish();
                  }
              }

              @Override
              protected String doInBackground(String... strings) {
                  HttpPost httpPost = new HttpPost();
                  try {
                      Log.d(TAG, json);
                      response = httpPost.post(SessionHelper.BaseUrl + "/CheckIn/api/User/AddUser", json);
                      ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
                      switch (responseStatusCodes){
                          case Success:
                              String serverUserID = new JSONObject(response).getJSONObject("Data").getString("UserId");
                              UserInfoDBHandler.InsertCheckinServerUserID(context, serverUserID);
                              SessionHelper.user = UserInfoDBHandler.FetchCurrentUserDetails(context);
                              break;
                          case Error:
                              break;
                      }
                      Log.d(TAG, response);
                  }catch (Exception e){
                      Log.e(TAG, e.getMessage());
                  }
                  return response;
              }
          };
            PostServerDetails.execute("");
            //addCheckInServerUserIDtoDB(response);
        }

}
