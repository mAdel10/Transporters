package com.memaro.transporter.Activities.Models;

public class LocationModelClass {
    private double Latitude, Longitude;
    private String companyName;

    public LocationModelClass() {
    }

    public LocationModelClass(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public LocationModelClass(double latitude, double longitude, String companyName) {
        Latitude = latitude;
        Longitude = longitude;
        this.companyName = companyName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
