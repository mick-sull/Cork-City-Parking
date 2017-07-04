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
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.micha.corkcityparking.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.micha.corkcityparking.Contants.SP_HOURS;
import static com.example.micha.corkcityparking.Contants.SP_SETTINGS;

/**
 * Created by micha on 01/06/2017.
 */

public class SettingFragment extends DialogFragment {
    MyDialogListener mListener;
    Context ctx;
    Dialog dialog;
    TextView lblSettingDistanceSelected;
    SharedPreferences prefs;
    private Number selectedValue;

    public SettingFragment(Context ctx, BottomNav listener){
        this.ctx = ctx;
        mListener = listener;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.frag_settings);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        lblSettingDistanceSelected = (TextView) dialog.findViewById(R.id.lblSettingHoursSelected);
        final CrystalSeekbar seekbar = (CrystalSeekbar) dialog.findViewById(R.id.rsHours);

        prefs = getActivity().getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        seekbar.setMinStartValue(prefs.getInt(SP_HOURS, 2)).apply();
        selectedValue = prefs.getInt(SP_HOURS, 2);
        lblSettingDistanceSelected.setText(prefs.getInt("distance", 2) + " hrs.");

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
                selectedValue = value;

            }
        });
        ButterKnife.bind(this, dialog);
        return dialog;
    }
    @OnClick(R.id.txtSettingsDone)
    public void doneText(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SP_HOURS, selectedValue.intValue());
        editor.commit();
        mListener.OnCloseDialog();
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
