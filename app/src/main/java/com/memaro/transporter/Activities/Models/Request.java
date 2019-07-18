package com.memaro.transporter.Activities.Models;

public class Request {

    private String goodType;
    private String goodWeight;
    private String payment;
    private String distance;
    private double userLatitude, userLongitude, orderLatitude, orderLongitude;
    private int price;
    private String requestId;

    public Request() {
    }

    public Request(String goodType, String goodWeight, String payment, String distance, double userLatitude, double userLongitude, double orderLatitude, double orderLongitude, int price, String requestId) {
        this.goodType = goodType;
        this.goodWeight = goodWeight;
        this.payment = payment;
        this.distance = distance;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.orderLatitude = orderLatitude;
        this.orderLongitude = orderLongitude;
        this.price = price;
        this.requestId = requestId;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(String goodweight) {
        this.goodWeight = goodweight;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getOrderLatitude() {
        return orderLatitude;
    }

    public void setOrderLatitude(double orderLatitude) {
        this.orderLatitude = orderLatitude;
    }

    public double getOrderLongitude() {
        return orderLongitude;
    }

    public void setOrderLongitude(double orderLongitude) {
        this.orderLongitude = orderLongitude;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
