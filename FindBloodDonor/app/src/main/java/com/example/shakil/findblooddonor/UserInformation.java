package com.example.shakil.findblooddonor;

/**
 * Created by Shakil on 17-Aug-18.
 */

public class UserInformation {
    private String donorName;
    private String contactNo;
    private String bloodGroup;

    public UserInformation(){

    }
    public UserInformation(String donorName, String contactNo, String bloodGroup) {
        this.donorName = donorName;
        this.contactNo = contactNo;
        this.bloodGroup = bloodGroup;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
