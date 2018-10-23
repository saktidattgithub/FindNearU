package com.search.findnearu;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

import java.util.List;


public class UserDataInput extends Activity implements View.OnClickListener {
    private EditText edit_name, edit_locality, edit_city, edit_state, edit_nation;
    private Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_input);
        edit_name = (EditText) findViewById(R.id.id_name);
        edit_locality = (EditText) findViewById(R.id.id_locality);
        edit_city = (EditText) findViewById(R.id.id_city);
        edit_state = (EditText) findViewById(R.id.id_state);
        edit_nation = (EditText) findViewById(R.id.id_nation);
        button_submit = (Button) findViewById(R.id.id_submit);
        button_submit.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.id_submit) {
            if (edit_locality.getText().toString().equals("")) {
                Toast.makeText(this, "Locality is mising", Toast.LENGTH_SHORT).show();
            } else if (edit_city.getText().toString().equals("")) {
                Toast.makeText(this, "City is mising", Toast.LENGTH_SHORT).show();
            } else if (edit_state.getText().toString().equals("")) {
                Toast.makeText(this, "State is mising", Toast.LENGTH_SHORT).show();
            } else if (edit_nation.getText().toString().equals("")) {
                Toast.makeText(this, "Nation is mising", Toast.LENGTH_SHORT).show();
            } else {
              /* StringBuffer buffer = new StringBuffer();
                buffer.append(edit_locality.getText().toString() + " " + edit_city.getText().toString() + " " + edit_state.getText().toString() +
                        " " + edit_nation.getText().toString());
                getLatLongFromGivenAddress(buffer.toString());*/
              startActivity(new Intent(UserDataInput.this,MainActivity.class));

            }
        }
    }

    public GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
                    (int) (location.getLongitude() * 1E6));

            return p1;
        } catch (Exception e) {

        }
        return  null;
    }
    public  void getLatLongFromGivenAddress(String address)
    {
        double lat= 0.0, lng= 0.0;

        Geocoder geoCoder = new Geocoder(this);
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0)
            {
                GeoPoint p = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));

                lat=p.getLatitudeE6()/1E6;
                lng=p.getLongitudeE6()/1E6;

                Log.d("Latitude", "" + lat);
                Log.d("Longitude", ""+lng);

                if(lat != 0.0 && lng != 0.0 )
                {
                    Toast.makeText(this, "Location found"+"Latitude" + lat +"  Longitude"+lng, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserDataInput.this,MainActivity.class));
                }else{
                    Toast.makeText(this, "Retry to get location", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserDataInput.this,MainActivity.class));
                }
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Please check your internet connectivity & location setting", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            startActivity(new Intent(UserDataInput.this,MainActivity.class));
        }
    }
}

