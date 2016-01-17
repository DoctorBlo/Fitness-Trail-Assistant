package com.example.msrazure.fitness.fragments;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msrazure.fitness.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Polina on 17.01.2016.
 */
public class MapFragment3 extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        LocationListener {

    MapView mMapView;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 40.648549;
        double longitude =  -73.982156;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("New York");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(40.648549, -73.982156)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        ArrayList<Location> listLocsToDraw = new ArrayList<>() ;

        Location loc1 = new Location("");
        loc1.setLatitude(40.648549);
        loc1.setLongitude(-73.982156);
        listLocsToDraw.add(loc1);

        Location loc2 = new Location("");
        loc2.setLatitude(40.649433);
        loc2.setLongitude(-73.981112);
        listLocsToDraw.add(loc2);


        drawPrimaryLinePath(listLocsToDraw);


        mGoogleApiClient
                = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        return v;
    }

    private void drawPrimaryLinePath( ArrayList<Location> listLocsToDraw )
    {
        if ( googleMap == null )
        {
            return;
        }

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor("#CC0000FF") );
        options.width( 5 );
        options.visible(true);

        for ( Location locRecorded : listLocsToDraw )
        {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(locRecorded.getLatitude(), locRecorded.getLongitude())).title("New York");

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

    private final GoogleApiClient.ConnectionCallbacks mConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
                LocationServices.FusedLocationApi.setMockMode(mGoogleApiClient, true);
                new MockLocationMovingHandler(mGoogleApiClient).start(48.873399, 2.342911);
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}