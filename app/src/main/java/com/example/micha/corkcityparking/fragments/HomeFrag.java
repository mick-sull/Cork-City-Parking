package com.example.micha.corkcityparking.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.micha.corkcityparking.ParkingAPI;
import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.RecyclerItemClickListener;
import com.example.micha.corkcityparking.adapters.ParkingAdapter;
import com.example.micha.corkcityparking.models.Parking;
import com.example.micha.corkcityparking.models.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    public HomeFrag() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }


    Context ctx;
    ParkingAdapter parkingAdapter;

    List<Record> parkingArray = new ArrayList<Record>();
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Toolbar toolbar;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.parking_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ctx = getActivity();
/*        android.app.ActionBar actionBar = getActivity().getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey_300)));
        //toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.grey_300)));
        actionBar.setTitle("Cork Parking Spaces");*/
     //  ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Cork Parking Spaces");

        //getData();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ctx, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        //changeActivity(position);
                    }


                })
        );
    }

    private void getData() {

        if (isNetworkAvailable(getContext())) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            ParkingAPI.Factory.getInstance().getParking().enqueue(new Callback<Parking>() {
                @Override
                public void onResponse(Call<Parking> call, Response<Parking> response) {
                    parkingArray = response.body().getResult().getRecords();
                        updateView();

                    mSwipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<Parking> call, Throwable t) {
                    Log.e("Failed", t.getMessage());
                }
            });
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            displayDialog();
        }
    }


    private void updateView() {


        try {
            toolbar.setSubtitle("Last updated: " + parkingArray.get(0).converteTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }



        recyclerView.setAdapter(new ParkingAdapter(parkingArray, R.layout.list_item_parking, ctx));

        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void displayDialog() {
        mSwipeRefreshLayout.setRefreshing(false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("No Internet Connection");
        alertDialogBuilder.setMessage("You are offline please check your internet connection");
        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
        alertDialogBuilder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

