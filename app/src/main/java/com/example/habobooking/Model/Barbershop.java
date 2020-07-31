package com.example.habobooking.Model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class Barbershop {

    @DocumentId
    private String id;
    @PropertyName("image 1")
    private String image1;
    @PropertyName("Image 2")
    private String image2;
    @PropertyName("Image 3")
    private String image3;
    private String address;
    private String suburb;
    private String prices;
    private String description;
    private String openingHours;
    private int phoneNumber;
    private int covidCapacity;

    public Barbershop() {
    }

    public Barbershop(String id, String image1, String image2, String image3, String address, String suburb, String prices, String description, String openingHours, int phoneNumber, int covidCapacity) {
        this.id = id;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.address = address;
        this.suburb = suburb;
        this.prices = prices;
        this.description = description;
        this.openingHours = openingHours;
        this.phoneNumber = phoneNumber;
        this.covidCapacity = covidCapacity;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("image 1")
    public String getImage1() {
        return image1;
    }
    @PropertyName("image 1")
    public void setImage1(String image1) {
        this.image1 = image1;
    }
    @PropertyName("Image 2")
    public String getImage2() {
        return image2;
    }
    @PropertyName("Image 2")
    public void setImage2(String image2) {
        this.image2 = image2;
    }
    @PropertyName("Image 3")
    public String getImage3() {
        return image3;
    }
    @PropertyName("Image 3")
    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCovidCapacity() {
        return covidCapacity;
    }

    public void setCovidCapacity(int covidCapacity) {
        this.covidCapacity = covidCapacity;
    }
}
