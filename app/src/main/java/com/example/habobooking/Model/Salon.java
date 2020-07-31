package com.example.habobooking.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Salon implements Parcelable {
    private String name,address,salonID;
    private String covidCapacity, description, image1, image2, image3;
    private String openingHours, phoneNumber, prices, streetAddress;

    //Street address is the actual address of the barber
    //Suburb such as "parramatta" refers to 'address'


    public Salon() {
    }

    //constructor with all the attributes

    public Salon(String name, String address, String salonID, String covidCapacity, String description,
                 String image1, String image2, String image3, String openingHours, String phoneNumber, String prices,
                 String streetAddress) {
        this.name =name;
        this.address = address;
        this.salonID = salonID;
        this.covidCapacity = covidCapacity;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.openingHours = openingHours;
        this.phoneNumber = phoneNumber;
        this.prices = prices;
        this.streetAddress = streetAddress;
    }


    //Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalonID() {
        return salonID;
    }

    public void setSalonID(String salonID) {
        this.salonID = salonID;
    }

    public String getCovidCapacity() {
        return covidCapacity;
    }

    public String getDescription() {
        return description;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPrices() {
        return prices;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    protected Salon(Parcel in) {
        name = in.readString();
        address = in.readString();
        salonID = in.readString();
    }

    public static final Creator<Salon> CREATOR = new Creator<Salon>() {
        @Override
        public Salon createFromParcel(Parcel in) {
            return new Salon(in);
        }

        @Override
        public Salon[] newArray(int size) {
            return new Salon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(salonID);
    }
}
