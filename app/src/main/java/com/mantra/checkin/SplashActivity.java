package com.mantra.checkin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.SignUp.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Session
        new SessionHelper(getApplicationContext());

        // Initialize Location
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        SessionHelper.mLocation = SessionHelper.mLocationUtility.getLastKnownLocation(getApplicationContext());

        // check if we need this kale
        // Launches loginActivity if UserInfo table is empty
//        if (!UserInfoDBHandler.CheckIfUserExistsInDB(getApplication().getApplicationContext())) {
//                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(i);
//        }
        //

        // todo Launch Background thread to finish initialization and then launch activity
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(SessionHelper.mR.getString(R.string.gps_enable_message))
                .setCancelable(false)
                .setPositiveButton(SessionHelper.mR.getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(SessionHelper.mR.getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
