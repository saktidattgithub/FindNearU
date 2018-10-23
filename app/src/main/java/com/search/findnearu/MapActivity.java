package com.search.findnearu;

import android.app.Activity;
import android.content.SharedPreferences;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity implements OnMapReadyCallback {
    private SharedPreferences location_preference;
    double lat=0.0,lang=0.0;
    String title;

    //--------------------------
    final long duration = 400;
    final Handler handler = new Handler();
    final long start = SystemClock.uptimeMillis();
GoogleMap _map;

    //-----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        location_preference= getSharedPreferences("location_pref",MODE_WORLD_READABLE);
        lat=getIntent().getDoubleExtra("lat",0.0);lang=getIntent().getDoubleExtra("lang",0.0);
       title=getIntent().getStringExtra("title");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment_id);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(Double.valueOf(location_preference.getString("lat","")),Double.valueOf(location_preference.getString("lang","")));
        final LatLng sydney1 = new LatLng(lat,lang);
        Projection proj = map.getProjection();
        _map=map;



/*
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));*/
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Double.valueOf(location_preference.getString("lat","")),Double.valueOf(location_preference.getString("lang",""))), 15));

        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_item_position))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .title(title)

                .position(new LatLng(lat, lang))).showInfoWindow();
        //----------------------------------------
       // final LatLng target = ...;
/*final MarkerOptions marker=new MarkerOptions();
        marker.title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_item_position));

        final long duration = 400;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
       // Projection proj = map.getProjection();

        Point startPoint = proj.toScreenLocation(sydney1);
        startPoint.y = 0;
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * sydney1.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * sydney1.latitude + (1 - t) * startLatLng.latitude;
                marker.position(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 10ms later.
                    handler.postDelayed(this, 10);
                } else {
                    // animation ended
                }
            }
        });*/

        //---------------------------------
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_item_position))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(Double.valueOf(location_preference.getString("lat","")),Double.valueOf(location_preference.getString("lang","")))).title("You are Here")).showInfoWindow();
//-------------------------
       /* map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation_arrow))
                .position(sydney1)
                .flat(true)
                .rotation(245));*/

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(sydney)
                .zoom(14)
                .bearing(90)
                .build();

        // Animate the change in camera view over 2 seconds
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);



       /* map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });*/
        //--------------------------------------------


    }
}