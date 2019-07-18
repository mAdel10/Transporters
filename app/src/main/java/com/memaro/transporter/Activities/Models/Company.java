package com.memaro.transporter.Activities.Models;

public class Company {

    private String companyId; //
    private String companyName; //
    private String companyEmail; //
    private String CompanyAddress;
    private String CompanyMarketNumber;
    private String imgUrl;
    private double latitude, longitude;

    public Company() {
    }

    public Company(String companyId, String companyName, String companyEmail, String imgUrl, double latitude, double longitude) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.imgUrl = imgUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Company(String companyId, String companyName, String companyEmail, String companyAddress, String companyMarketNumber, String imgUrl, double latitude, double longitude) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.CompanyAddress = companyAddress;
        this.CompanyMarketNumber = companyMarketNumber;
        this.imgUrl = imgUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyMarketNumber() {
        return CompanyMarketNumber;
    }

    public void setCompanyMarketNumber(String companyMarketNumber) {
        CompanyMarketNumber = companyMarketNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

}