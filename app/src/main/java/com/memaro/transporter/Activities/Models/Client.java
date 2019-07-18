package com.memaro.transporter.Activities.Models;

public class Client {

    private String clientId;
    private String firstName;
    private String email;
    private String phone;
    private String imgUrl;

    public Client() {
    }

    public Client(String clientId, String firstName, String email, String phone, String imgUrl) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
