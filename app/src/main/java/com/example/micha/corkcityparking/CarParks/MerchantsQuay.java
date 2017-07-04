package com.example.micha.corkcityparking.CarParks;

import java.text.DecimalFormat;

/**
 * Created by micha on 01/06/2017.
 */

public class MerchantsQuay implements CarPark {
    @Override
    public String formattedCost(double hours) {
        DecimalFormat formatter = new DecimalFormat("€0.00");
        return formatter.format(getParkingCost(hours));
    }

    @Override
    public double getParkingCost(double hours) {
        if(hours <= 1)
            return 2.2;
        else if (hours <= 2)
            return 4.4;
        else if (hours <= 3)
            return 6.6;
        else if (hours <= 4)
            return 8.8;
        else if (hours <= 5)
            return 12;
        else if (hours <= 6)
            return 16;
        else if (hours <= 7)
            return 20;
        else if (hours <= 8)
            return 25;
        else if (hours <= 9)
            return 30;
        else if (hours <= 10)
            return 40;
        else if (hours > 10)
            return 50;
        else
            return 0;
    }


    public String getRates() {
        return ("Price \n" +
                "€2.20  \n" +
                "€4.40  \n" +
                "€6.60  \n" +
                "€8.80  \n" +
                "€12.00  \n" +
                "€16.00 \n" +
                "€20.00 \n" +
                "€25.00 \n" +
                "€30.00 \n" +
                "€40.00 \n" +
                "€50.00 \n");
    }


    public String getDurationsText(){
        return (
                "Duration\n" +
                        "Up to 1 hour\n" +
                        "Up to 2 hours\n" +
                        "Up to 3 hours\n" +
                        "Up to 4 hours\n" +
                        "Up to 5 hours\n" +
                        "Up to 6 hours\n" +
                        "Up to 7 hours\n" +
                        "Up to 8 hours\n" +
                        "Up to 9 hours\n" +
                        "Up to 10 hours\n" +
                        "10+ hours\n");
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
        return ("08:00 - 21:00\n" +
                "08:00 - 21:00\n" +
                "08:00 - 21:00\n" +
                "08:00 - 21:00\n" +
                "08:00 - 21:00\n" +
                "08:00 - 19:00\n" +
                "11:00 - 18:30\n");
    }
}
