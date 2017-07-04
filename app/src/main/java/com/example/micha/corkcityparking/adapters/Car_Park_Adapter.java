package com.example.micha.corkcityparking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.micha.corkcityparking.R;
import com.example.micha.corkcityparking.models.Record;

import java.util.List;

/**
 * Created by micha on 08/06/2017.
 */

public class Car_Park_Adapter extends RecyclerView.Adapter<Car_Park_Adapter.CarParkViewHolder>{
    List<Record> carparks;
    Context ctx;

    public class CarParkViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        public CarParkViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.lblCarparkName);
        }

    }

    public Car_Park_Adapter(List<Record> carparks, Context ctx){
        Log.d("TEST",  "" + carparks.size());
        this.carparks = carparks;
        this.ctx = ctx;

    }



    @Override
    public CarParkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carpark_item, parent, false);

        return new CarParkViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(Car_Park_Adapter.CarParkViewHolder holder, int position) {
        holder.title.setText((carparks.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return carparks.size();
    }




}
