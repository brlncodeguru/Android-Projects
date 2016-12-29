package com.example.lakshminarayanabr.inclass12maps;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;

import android.location.LocationListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener mLocationListener;

    Polyline polyline;

    Boolean isTrackingStarted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    PolylineOptions polyLineOptions = new PolylineOptions();
    boolean isTracking = false;
    Marker startMarker, stopMarker;
    List<Marker> removedMarkers = new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Not Ready").setMessage("Do you want to enable GPS?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);


                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    finish();

                }
            });
            builder.create();
            builder.show();

        } else {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Log.d("Location", location.getLatitude() + "," + location.getLongitude());
                    if (mMap != null) {

                        //mMap.clear();

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("Origin"));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 10.0f));


                        if (isTracking) {
                            polyLineOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
                            polyline = mMap.addPolyline(polyLineOptions);
                            if (stopMarker!=null) {
                                stopMarker.remove();
                                removedMarkers.add(stopMarker);
                            }
                            stopMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude()
                                    ,location.getLongitude())).title("Stop Marker"));

                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(getBounds(), 70);
                            mMap.animateCamera(cu);



                        }

                        if(!isTrackingStarted)
                        {

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude()
                                    , location.getLongitude()),9.5f));
                            isTrackingStarted = true;



                        }
                    }


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1000, (android.location.LocationListener) mLocationListener);

            }
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1000,  mLocationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1000, (android.location.LocationListener) mLocationListener);



                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                isTracking = !isTracking;
                Log.d("demo", "Tracking listener");
                if (isTracking) {
                    startMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Start Marker"));
                    polyLineOptions.add(new LatLng(latLng.latitude, latLng.longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    Toast.makeText(MapsActivity.this,"Tracking Started",Toast.LENGTH_LONG).show();


                }else{
                    mMap.clear();
                    removedMarkers = new ArrayList<Marker>();
                    polyLineOptions = new PolylineOptions();
                    Toast.makeText(MapsActivity.this,"Tracking Stopped",Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    public LatLngBounds getBounds(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startMarker.getPosition());
        for (Marker marker : removedMarkers) {
            builder.include(marker.getPosition());
        }

        builder.include(stopMarker.getPosition());

        return builder.build();
    }

}
