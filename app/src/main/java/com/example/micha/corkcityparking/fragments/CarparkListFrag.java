package com.example.micha.corkcityparking.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.RecyclerItemClickListener;
import com.example.micha.corkcityparking.adapters.Car_Park_Adapter;
import com.example.micha.corkcityparking.models.Record;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.example.micha.corkcityparking.Contants.SP_SELECTED_CARPARK;
import static com.example.micha.corkcityparking.Contants.SP_SETTINGS;

/**
 * Created by micha on 08/06/2017.
 */

public class CarparkListFrag extends DialogFragment {

    Dialog dialog;
    private RecyclerView recyclerView;
    Context ctx;
    List<Record> parkingArray = new ArrayList<Record>();
    private Car_Park_Adapter mAdapter;
    CarParkListDialogListener mListener;
    SharedPreferences prefs;

    public CarparkListFrag(Context ctx, List<Record> parkingArray) {
        this.ctx = ctx;
        this.parkingArray = parkingArray;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CarParkListDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.carpark_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        prefs = getActivity().getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.rvDialogCarparkView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ctx, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt(SP_SELECTED_CARPARK, parkingArray.get(position).getId().intValue());
                        editor.apply();

                        mListener.dialogListener(parkingArray.get(position));
                        dialog.dismiss();
                    }
                })


        );

        mAdapter = new Car_Park_Adapter(parkingArray, ctx);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        ButterKnife.bind(this, dialog);
        return dialog;
    }


    public interface CarParkListDialogListener {
        void dialogListener(Record selectedCarPark);
    }
}
