package com.example.micha.corkcityparking.CarParks;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by micha on 25/09/2016.
 */

public class NorthMainStreet implements CarPark {
    Time currentTime;
    double cost;
    public void ParkingDetailsInterface(Time timeParked) throws ParseException {
        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
        java.util.Date date1 = df.parse(currentTime.toString());
        java.util.Date date2 = df.parse(timeParked.toString());
        long diff = date2.getTime() - date1.getTime();
        //calculateCost(currentTime.getTime() - timeParked.getTime());
        calculateCost(diff);
    }


    private void calculateCost(long diff) {

    }


    public double getParkingCost(double hours) {
        if(hours <= 1)
            return 1.70;
        else if (hours <= 2)
            return 3.50;
        else if (hours <= 3)
            return 5;
        else if (hours <= 4)
            return 7;
        else if (hours <= 5)
            return 9;
        else if (hours <= 6)
            return 12;
        else if (hours <= 7)
            return 14;
        else if (hours <= 8)
            return 16;
        else if (hours <= 9)
            return 18;
        else if (hours <= 10)
            return 20;
        else if (hours > 10)
            return 22;
        else
            return 0;
    }

    public String getRates() {
        return ("Price \n" +
                "€1.70  \n" +
                "€3.50  \n" +
                "€5.00  \n" +
                "€7.00  \n" +
                "€9.00  \n" +
                "€12.00 \n" +
                "€14.00 \n" +
                "€16.00 \n" +
                "€18.00 \n" +
                "€20.00 \n" +
                "€22.00 \n" +
                "€22.00 \n" +
                "€12.90");
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
                        "Up to 9 hours\n" +
                        "Up to 10 hours\n" +
                        "10+ hours\n" +
                        "Maximum Charge per Day\n" +
                        "Overnight Charge");
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
        return ("08:30 - 18:30\n" +
                "08:30 - 18:30\n" +
                "08:30 - 18:30\n" +
                "08:30 - 18:30\n" +
                "08:30 - 18:30\n" +
                "08:30 - 18:30\n" +
                "Closed\n");
    }


    @Override
    public String formattedCost(double hours) {
        DecimalFormat formatter = new DecimalFormat("€0.00");
        return formatter.format(getParkingCost(hours));
    }
}
