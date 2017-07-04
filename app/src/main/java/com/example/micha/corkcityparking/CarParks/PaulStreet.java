package com.example.micha.corkcityparking.CarParks;

import java.text.DecimalFormat;

/**
 * Created by micha on 01/06/2017.
 */

public class PaulStreet implements CarPark {
    @Override
    public String formattedCost(double hours) {
        DecimalFormat formatter = new DecimalFormat("€0.00");
        return formatter.format(getParkingCost(hours));
    }

    @Override
    public double getParkingCost(double hours) {
        if(hours <= 1)
            return 2.30;
        else if (hours <= 2)
            return 4.6;
        else if (hours <= 3)
            return 6.9;
        else if (hours <= 4)
            return 10;
        else if (hours <= 5)
            return 12.5;
        else if (hours >= 5)
            return (((hours - 5) * 2.5) + 12.5);
        else
            return 0;
    }

    @Override
    public String getRates() {
        return ("Price \n" +
                "€2.30  \n" +
                "€4.60  \n" +
                "€6.90  \n" +
                "€10.00  \n" +
                "€12.50  \n" +
                "€2.50 \n");
    }

    @Override
    public String getDurationsText(){
        return (
                "Duration\n" +
                        "Up to 1 hour\n" +
                        "Up to 2 hours\n" +
                        "Up to 3 hours\n" +
                        "Up to 4 hours\n" +
                        "Up to 5 hours\n" +
                        "Every Subsequent Hour\n");
    }
    @Override
    public String getOpeningDays(){
        return ("Monday\n" +
                "Tuesday\n" +
                "Wednesday\n" +
                "Thursday\n" +
                "Friday\n" +
                "Saturday\n" +
                "Sunday\n");

    }
    @Override
    public String getTimes(){
        return ("07:30 - Midnight\n" +
                "07:30 - Midnight\n" +
                "07:30 - Midnight\n" +
                "07:30 - Midnight\n" +
                "07:30 - Midnight\n" +
                "07:30 - Midnight\n" +
                "11:30 - Midnight\n");
    }
}
