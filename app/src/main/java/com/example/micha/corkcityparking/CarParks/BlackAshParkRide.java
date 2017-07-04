package com.example.micha.corkcityparking.CarParks;

import java.text.DecimalFormat;

/**
 * Created by micha on 01/06/2017.
 */

public class BlackAshParkRide implements CarPark {

    @Override
    public String formattedCost(double hours) {
        DecimalFormat formatter = new DecimalFormat("€0.00");
        return formatter.format(getParkingCost(hours));
    }

    @Override
    public double getParkingCost(double hours) {
        return 5.00;
    }


    public String getRates() {
        return ("Price \n" +
                "€5.00  \n" );
    }


    public String getDurationsText(){
        return (
                "Duration\n" +
                        "Per Day(Bus Inc.)\n");
    }

    public String getOpeningDays(){
        return ("Monday\n" +
                "Tuesday\n" +
                "Wednesday\n" +
                "Thursday\n" +
                "Friday\n" +
                "Saturday\n" +
                "Sunday\n");

    }

    public String getTimes(){
        return ("07:30 - 19:30\n" +
                "07:30 - 19:30\n" +
                "07:30 - 19:30\n" +
                "07:30 - 19:30\n" +
                "07:30 - 19:30\n" +
                "07:30 - 19:30\n" +
                "Closed\n");
    }
}
