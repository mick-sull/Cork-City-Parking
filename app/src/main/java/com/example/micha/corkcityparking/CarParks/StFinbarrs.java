package com.example.micha.corkcityparking.CarParks;

import java.text.DecimalFormat;

/**
 * Created by micha on 01/06/2017.
 */

public class StFinbarrs implements CarPark {

    @Override
    public String formattedCost(double hours) {
        DecimalFormat formatter = new DecimalFormat("€0.00");
        return formatter.format(getParkingCost(hours));
    }

    @Override
    public double getParkingCost(double hours) {
        if(hours * 2.8 > 18)
            return 18;
        else
            return hours * 2.8;
    }


    public String getRates() {
        return ("Price \n" +
                "€2.80  \n" +
                " \n" +
                "  \n" +
                "€5.00  \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "€13.00 \n" +
                "\n" +
                "\n" +
                "€15.00 \n" +
                "\n" +
                "\n" +
                "\n" +
                "€11.00 \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "€45.00 \n");
    }


    public String getDurationsText(){
        return (
                "Duration\n" +
                "Per Hour\n" +
                "  \n" +
                "Evening\n" +
                "5pm to 10pm (Pay-on-Arrival)\n" +
                "\n" +
                "\n" +
                "Prebook Online\n" +
                "Day Parking \n(8am to 6pm)\n" +
                "\n" +
                "\n" +
                "24 Hour Parking\n" +
                        "  \n" +
                        "  \n" +

                "4 Hour Shopper Special\n" +
                        "  \n" +
                "Weekend\nFriday after 12pm to Monday before 12pm\n");
    }

    public String getOpeningDays(){
        return ("24/7");

    }

    public String getTimes(){
        return ("");
    }
}
