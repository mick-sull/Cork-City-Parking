package com.example.micha.corkcityparking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.micha.corkcityparking.Contants.SP_SELECTED_CARPARK;
import static com.example.micha.corkcityparking.Contants.SP_SETTINGS;


public class InfoFrag extends Fragment  implements CarparkListFrag.CarParkListDialogListener{
//public class InfoFrag extends AppCompatActivity {
    @BindView(R.id.ibParkingIcon)
    ImageButton ibParkingIcon;
    List<Record> parkingArray = new ArrayList<Record>();
    @BindView(R.id.txtCarpark)
    TextView lblCarpark;
    @BindView(R.id.txtRatesDes)
    TextView lblRatesDes;

    @BindView(R.id.txtRatesCost) TextView lblRatesCost;
    @BindView(R.id.txtDayDes) TextView lblDayDes;
    @BindView(R.id.txtDayTimes) TextView lblDayTimes;
    @BindView(R.id.layoutSelectCarpark)
    LinearLayout layoutSelectCarpark;
    SharedPreferences prefs;

    public InfoFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        getData();

    }

    private void getData() {
        ParkingAPI.Factory.getInstance().getParking().enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                parkingArray = response.body().getResult().getRecords();
                for(int i = 0; i<parkingArray.size(); i++){
                    if(prefs.getInt(SP_SELECTED_CARPARK, 1) == parkingArray.get(i).getId()){
                        updateView(parkingArray.get(i));
                    }
                }
                Toast.makeText(getContext(), "onResponse Size: " + parkingArray.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.ibParkingIcon, R.id.layoutSelectCarpark})
    public void ibParkingIcon(){
        //FragmentManager fm = getFragmentManager();
        android.app.FragmentManager fm = getActivity().getFragmentManager();
        CarparkListFrag editNameDialogFragment = new CarparkListFrag(getContext(), parkingArray);
        editNameDialogFragment.show(fm, "abc");


    }

    @Override
    public void dialogListener(Record selectedCarPark) {

    }

    public void updateView(Record selectedCarPark){
        CarPark carPark = null;
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
        //StFinbarrs nms = new StFinbarrs();
        lblRatesDes.setText(carPark.getDurationsText());
        lblRatesCost.setText(carPark.getRates());
        lblDayDes.setText(carPark.getOpeningDays());
        lblDayTimes.setText(carPark.getTimes());

        //Update the shared preferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SP_SELECTED_CARPARK, selectedCarPark.getId().intValue());
        editor.apply();
        //Toast.makeText(getContext(), "Selected park: " + selectedCarPark.getName(), Toast.LENGTH_SHORT).show();
    }
}
