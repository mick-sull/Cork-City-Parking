package com.example.micha.corkcityparking.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.micha.corkcityparking.CarParks.BlackAshParkRide;
import com.example.micha.corkcityparking.CarParks.CarPark;
import com.example.micha.corkcityparking.CarParks.CarrollsQuay;
import com.example.micha.corkcityparking.CarParks.CityHall;
import com.example.micha.corkcityparking.CarParks.GrandParade;
import com.example.micha.corkcityparking.CarParks.MerchantsQuay;
import com.example.micha.corkcityparking.CarParks.NorthMainStreet;
import com.example.micha.corkcityparking.CarParks.PaulStreet;
import com.example.micha.corkcityparking.CarParks.StFinbarrs;
import com.example.micha.corkcityparking.Notify;
import com.example.micha.corkcityparking.ParkingAPI;
import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.models.Parking;
import com.example.micha.corkcityparking.models.Record;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.micha.corkcityparking.Contants.SP_HOURS;
import static com.example.micha.corkcityparking.Contants.SP_RECEIVE_NOTIFICATION;
import static com.example.micha.corkcityparking.Contants.SP_SELECTED_CARPARK;
import static com.example.micha.corkcityparking.Contants.SP_SETTINGS;

/**
 * Created by micha on 03/07/2017.
 */

public class ParkCarDialog extends DialogFragment  {
    SettingFragment.MyDialogListener mListener;
    Context ctx;
    Dialog dialog;
    List<Record> parkingArray = new ArrayList<Record>();
    TextView lblSettingDistanceSelected;
    SharedPreferences prefs;
    @BindView(R.id.ibParkingIconPC)
    ImageButton ibParkingIcon;
    Record selectedCarPark;
    @BindView(R.id.txtCarparkPC)
    TextView lblCarpark;
    @BindView(R.id.lblParkingCost)
    TextView lblParkingCost;
    @BindView(R.id.cbReceiveNotifiactions)
    CheckBox cbReceiveNotifiactions;
    CarPark carPark;
    SharedPreferences.Editor editor;
    Notify notification = null;


    public ParkCarDialog(Context ctx, BottomNav listener){
        this.ctx = ctx;
        mListener = listener;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_park_car);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getData();
        lblSettingDistanceSelected = (TextView) dialog.findViewById(R.id.lblSettingHoursSelectedPC);
        final CrystalSeekbar seekbar = (CrystalSeekbar) dialog.findViewById(R.id.rsHoursParkCar);

        prefs = getActivity().getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        seekbar.setMinStartValue(prefs.getInt(SP_HOURS, 2)).apply();
        lblSettingDistanceSelected.setText(prefs.getInt("distance", 2) + " hrs.");
        editor = prefs.edit();

        // set listener
        seekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                lblSettingDistanceSelected.setText(String.valueOf(minValue) + " hrs.");
            }
        });

// set final value listener
        seekbar.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                Log.d("CRS=>", String.valueOf(value));
                lblParkingCost.setText(carPark.formattedCost(value.doubleValue()));
                //SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(SP_HOURS, value.intValue());
                editor.commit();
            }
        });
        ButterKnife.bind(this, dialog);
        return dialog;
    }
    private void getData() {
        ParkingAPI.Factory.getInstance().getParking().enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                parkingArray = response.body().getResult().getRecords();
                for(int i = 0; i<parkingArray.size(); i++){
                    if(prefs.getInt(SP_SELECTED_CARPARK, 1) == parkingArray.get(i).getId()){
                        updateView(parkingArray.get(i));
                        selectedCarPark = parkingArray.get(i);
                    }
                }
//                Toast.makeText(getContext(), "onResponse Size: " + parkingArray.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }


    @OnClick(R.id.ibParkingIconPC)
    public void ibParkingIcon(){
        //FragmentManager fm = getFragmentManager();
        android.app.FragmentManager fm = getActivity().getFragmentManager();
        CarparkListFrag editNameDialogFragment = new CarparkListFrag(getContext(), parkingArray);
        editNameDialogFragment.show(fm, "abc");


    }

    public void updateView(Record selectedCarPark) {
        carPark = null;
        switch (selectedCarPark.getId()) {
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
        lblCarpark.setText(selectedCarPark.getName());
        lblParkingCost.setText(carPark.formattedCost(prefs.getInt(SP_HOURS, 2)));
    }


    @OnClick(R.id.txtStartPC)
    public void doneText() {
        //mListener.OnCloseDialog();
        editor.putBoolean(SP_RECEIVE_NOTIFICATION,cbReceiveNotifiactions.isChecked());
        notification = new Notify(ctx);
        notification.displayNotification(selectedCarPark,prefs.getInt(SP_HOURS, 2));

        editor.commit();
        dialog.dismiss();


    }

    public interface MyDialogListener {
        void OnCloseDialog();
    }
    @OnClick(R.id.txtSettingsCancel)
    public void cancelRequest(){

        dialog.dismiss();
        dialog.cancel();

    }
}
