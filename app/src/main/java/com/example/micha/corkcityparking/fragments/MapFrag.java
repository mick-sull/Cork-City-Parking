package com.example.micha.corkcityparking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.micha.corkcityparking.CarParks.BlackAshParkRide;
import com.example.micha.corkcityparking.CarParks.CarPark;
import com.example.micha.corkcityparking.CarParks.CarrollsQuay;
import com.example.micha.corkcityparking.CarParks.CityHall;
import com.example.micha.corkcityparking.CarParks.GrandParade;
import com.example.micha.corkcityparking.CarParks.MerchantsQuay;
import com.example.micha.corkcityparking.CarParks.NorthMainStreet;
import com.example.micha.corkcityparking.CarParks.PaulStreet;
import com.example.micha.corkcityparking.CarParks.StFinbarrs;
import com.example.micha.corkcityparking.ParkingAPI;
import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.models.Parking;
import com.example.micha.corkcityparking.models.Record;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.micha.corkcityparking.Contants.SP_HOURS;
import static com.example.micha.corkcityparking.Contants.SP_SETTINGS;


public class MapFrag extends Fragment implements OnMapReadyCallback, SettingFragment.MyDialogListener {

    private View rootView;
    MapView mMapView;
    private GoogleMap googleMap;
    List<Record> parkingArray = new ArrayList<Record>();
    SharedPreferences prefs;
    BottomNav mListener;

    public MapFrag() {
        // Required empty public constructor

    }

    public MapFrag(BottomNav listener) {
        mListener = listener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        prefs = getActivity().getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        Log.d("MAPFRAG", "onCreate");
        mMapView.onResume();// needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        // Perform any camera updates here
        return v;
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

    @Override
    public void onMapReady(final GoogleMap mMap) {
        // latitude and longitude
        LatLng eventLoc = new LatLng(51.903614, -8.468399);

        ParkingAPI.Factory.getInstance().getParking().enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                parkingArray = response.body().getResult().getRecords();
                setMarkers();

            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });


        Log.d("MAPFRAG", "onMapReady Called");
        googleMap = mMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLoc, 13));
        //ab.setTitle(location.getLocationName());

    }

    public void setMarkers() {
        googleMap.clear();
        for (int i = 0; i < parkingArray.size(); i++) {
            CarPark carPark = null;
            switch (parkingArray.get(i).getId()) {
                case 1:
                    carPark = new PaulStreet();
                    break;
                case 2:
                    carPark = new NorthMainStreet();
                    break;
                case 3:
                    carPark = new BlackAshParkRide();
                    break;
                case 4:
                    carPark = new CityHall();
                    break;
                case 5:
                    carPark = new CarrollsQuay();
                    break;
                case 6:
                    carPark = new GrandParade();
                    break;
                case 7:
                    carPark = new MerchantsQuay();
                    break;
                case 8:
                    carPark = new StFinbarrs();
                    break;
            }
            LatLng carParkLoc = new LatLng(parkingArray.get(i).getLatitude(), parkingArray.get(i).getLongitude());
            //.title(carPark.formattedCost(2)));
            IconGenerator iconFactory = new IconGenerator(getContext());
            MarkerOptions markerOptions = new MarkerOptions().
                    icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(carPark.formattedCost(prefs.getInt(SP_HOURS, 2))))).
                    position(carParkLoc).
                    anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()).title(parkingArray.get(i).getName() + " (" + parkingArray.get(i).getFreeSpaces() + ")");
            googleMap.addMarker(markerOptions);
        }
    }

    public void updateMarkers(){
        setMarkers();
    }

    @Override
    public void OnCloseDialog() {
        mMapView.getMapAsync(this);
    }

    public interface MyDialogListener {
        void OnCloseDialog();
    }
}
