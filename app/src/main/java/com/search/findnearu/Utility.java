package com.search.findnearu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by Saktidatt on 11-09-2015.
 */
public class Utility {

  // public static String latitude="19.039680";
   // public static String longitude="73.099857";
    public static String SERVER_KEY="AIzaSyCT3Ts4YwUMPmifHAQxu2TDZid8tANfNYs";
    public static String latitude="";
    public static String longitude="";
    public static String Location_URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=2000&types=food&key=AIzaSyAvj49ec2E8S4a5N8H79RFs3oAmt5Dronc";
    public static String Location_URL_FOOD="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=20000&types=food&key=AIzaSyAvj49ec2E8S4a5N8H79RFs3oAmt5Dronc";
    public static String Location_URL_Medical="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=20000&types=health&key=AIzaSyAvj49ec2E8S4a5N8H79RFs3oAmt5Dronc";
    public static String Location_URL_Education="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=20000&types=school&key=AIzaSyAvj49ec2E8S4a5N8H79RFs3oAmt5Dronc";
    public static String Location_URL_Finance="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=20000&name=bank&key=AIzaSyAvj49ec2E8S4a5N8H79RFs3oAmt5Dronc";
Activity _activity;
    public Utility(Activity activity)
    {
        _activity=activity;
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);

        // Setting Dialog Title
        alertDialog.setTitle("Error!");

        // Setting Dialog Message
        alertDialog.setMessage("Please check your location setting");

        // On pressing Settings button
        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        _activity.startActivity(intent);
                    }
                });

        alertDialog.show();
    }

    public void DataConnectionAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);

        // Setting Dialog Title
        alertDialog.setTitle("Error!");

        // Setting Dialog Message
        alertDialog.setMessage("Please Restart app with Internet");

        // On pressing Settings button
        alertDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       _activity.finish();
                    }
                });

        alertDialog.show();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) _activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
