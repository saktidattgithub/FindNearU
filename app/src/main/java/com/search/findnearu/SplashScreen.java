package com.search.findnearu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class SplashScreen extends Activity implements LocationListener{
    private static int SPLASH_TIME_OUT = 3000;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    SharedPreferences location_preference;
    SharedPreferences.Editor editor_location_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        location_preference=getSharedPreferences("location_pref",MODE_WORLD_READABLE);
        editor_location_preference=location_preference.edit();
// Getting the name of the best provider
        Animation fadeoutAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fadeout);
        View vw=getLayoutInflater().inflate(R.layout.activity_splash_screen,null);
        vw.startAnimation(fadeoutAnimation);

        //---------------------
        //---------------
        if(criteria!=null) {
            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null) {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
                //------------------

                //----------
                new Handler().postDelayed(new Runnable() {



                    @Override
                    public void run() {

                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                       startActivity(i);

                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }else{
                showSettingsAlert();
            }
        }else {
            showSettingsAlert();
        }

    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Error!");

        // Setting Dialog Message
        alertDialog.setMessage("Please ");

        // On pressing Settings button
        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        Utility.latitude=String.valueOf(location.getLatitude());
        Utility.longitude=String.valueOf(location.getLongitude());
        editor_location_preference.putString("lat",String.valueOf(location.getLatitude()));
        editor_location_preference.putString("lang",String.valueOf(location.getLongitude()));
        editor_location_preference.putString("distance",String.valueOf(1000));
        editor_location_preference.commit();
        //Intent i = new Intent(SplashScreen.this, MainActivity.class);
        //startActivity(i);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
