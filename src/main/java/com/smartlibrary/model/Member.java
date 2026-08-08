package com.smartlibrary.model;
public class Member {
    private int mId;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String contactInfo;
    private String street;
    private String city;
    private String zipcode;
    public int getmId() { return mId; }
    public void setmId(int mId) { this.mId = mId; }
    public String getmName() { return mName; }
    public void setmName(String mName) { this.mName = mName; }
    public String getmEmail() { return mEmail; }
    public void setmEmail(String mEmail) { this.mEmail = mEmail; }
    public String getmPassword() { return mPassword; }
    public void setmPassword(String mPassword) { this.mPassword = mPassword; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
}
