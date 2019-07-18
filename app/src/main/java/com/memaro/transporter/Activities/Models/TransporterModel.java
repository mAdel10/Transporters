package com.memaro.transporter.Activities.Models;

public class TransporterModel {

    private String id;
    private String name;
    private String phoneNumber;
    private String driverId;
    private String email;
    private String password;
    private String carType;
    private String carNumber;
    private double latitude, longitude;
    private String driverImageUrl;

    public TransporterModel() {
    }

    public TransporterModel(String id, String name, String phoneNumber, String driverId, String email,
                            String password, String carType, String carNumber,
                            double latitude, double longitude, String driverImageUrl) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.driverId = driverId;
        this.email = email;
        this.password = password;
        this.carType = carType;
        this.carNumber = carNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.driverImageUrl = driverImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDriverImageUrl() {
        return driverImageUrl;
    }

    public void setDriverImageUrl(String driverImageUrl) {
        this.driverImageUrl = driverImageUrl;
    }
}
