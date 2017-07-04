package com.example.micha.corkcityparking.CarParks;

/**
 * Created by micha on 01/06/2017.
 */

public interface CarPark {
    public String formattedCost(double hours);
    public double getParkingCost(double hours);
    public String getRates();
    public String getDurationsText();
    public String getOpeningDays();
    public String getTimes();

}
