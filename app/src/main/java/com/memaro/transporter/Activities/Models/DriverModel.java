package com.memaro.transporter.Activities.Models;

public class DriverModel {

    private String name;
    private String phoneNumber;
    private String whatsAppNumber;
    private String emailFacebook;
    private String email;
    private String driveId;
    private String Password;

    public DriverModel() {
    }

    public DriverModel(String name, String phoneNumber, String whatsAppNumber, String emailFacebook,
                       String email, String driveId, String Password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.whatsAppNumber = whatsAppNumber;
        this.emailFacebook = emailFacebook;
        this.email = email;
        this.driveId = driveId;
        this.Password = Password;
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

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    public String getEmailFacebook() {
        return emailFacebook;
    }

    public void setEmailFacebook(String emailFacebook) {
        this.emailFacebook = emailFacebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String randomPassword) {
        this.Password = randomPassword;
    }

}
