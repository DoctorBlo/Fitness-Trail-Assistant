package com.example.msrazure.fitness.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msrazure.fitness.R;
import com.example.msrazure.fitness.providers.MockLocationProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.bitbar.testdroid.testapp.TestdroidTestAppActivity;
//import com.bitbar.recorder.extensions.ExtSolo


import java.util.ArrayList;

/**
 * Created by Polina on 22.01.2016.
 */
public class MapFragment5 extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    LocationManager lm;
    private LatLng latlng;
    MapView mMapView;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    MockLocationProvider mock;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();


        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity().getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        // Perform any camera updates here
        ArrayList<Location> listLocsToDraw = new ArrayList<>() ;

        Location loc1 = new Location("Exercise1");
        loc1.setLatitude(49.619087);
        loc1.setLongitude( 6.156554);
        listLocsToDraw.add(loc1);

        Location loc2 = new Location("Exercise2");
        loc2.setLatitude(49.619657);
        loc2.setLongitude( 6.158210);
        listLocsToDraw.add(loc2);

        Location loc3 = new Location("Exercise3");
        loc3.setLatitude(49.622164);
        loc3.setLongitude(6.164522);
        listLocsToDraw.add(loc3);
        drawPrimaryLinePath(listLocsToDraw);
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, this.getActivity().getApplicationContext());
        //Set test location
        mock.pushLocation(49.619087, 6.456554);


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100000); // Update location every second



        if (ActivityCompat.checkSelfPermission(this.getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Fitness_App", "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Fitness_App", "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        latlng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng,14);
        googleMap.animateCamera(cameraUpdate);

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(location.getLatitude(), location.getLongitude())).title("MyLocation" + location.getLatitude() + location.getLongitude());

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // adding marker
        googleMap.addMarker(marker);
    }

    private void drawPrimaryLinePath( ArrayList<Location> listLocsToDraw )
    {
        if ( googleMap == null )return;
        if ( listLocsToDraw.size() < 2 )return;

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor("#CC0000FF") );
        options.width(5);
        options.visible(true);

        for ( Location locRecorded : listLocsToDraw )
        {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(locRecorded.getLatitude(), locRecorded.getLongitude())).title( locRecorded.toString());
            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            googleMap.addMarker(marker);
            options.add( new LatLng( locRecorded.getLatitude(),
                    locRecorded.getLongitude() ) );
        }
        googleMap.addPolyline(options);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
