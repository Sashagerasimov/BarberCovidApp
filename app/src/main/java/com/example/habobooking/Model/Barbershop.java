package com.example.habobooking.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class Barbershop implements Parcelable {

    @DocumentId
    private String id;
    @PropertyName("image1")
    private String image1;
    @PropertyName("Image2")
    private String image2;
    @PropertyName("image3")
    private String image3;
    @PropertyName("Name")
    private String name;
    @PropertyName("Address")
    private String address;
    @PropertyName("Suburb")
    private String suburb;
    @PropertyName("Prices")
    private String prices;
    @PropertyName("Description")
    private String description;
    @PropertyName("OpeningH")
    private String openingHours;
    @PropertyName("Phone")
    private String phoneNumber;
    @PropertyName("Covid19C")
    private int covidCapacity;

    public Barbershop() {
    }

    public Barbershop(String id, String image1, String image2, String image3, String name, String address, String suburb, String prices, String description, String openingHours, String phoneNumber, int covidCapacity) {
        this.id = id;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.name = name;
        this.address = address;
        this.suburb = suburb;
        this.prices = prices;
        this.description = description;
        this.openingHours = openingHours;
        this.phoneNumber = phoneNumber;
        this.covidCapacity = covidCapacity;
    }

    protected Barbershop(Parcel in) {
        id = in.readString();
        image1 = in.readString();
        image2 = in.readString();
        image3 = in.readString();
        name = in.readString();
        address = in.readString();
        suburb = in.readString();
        prices = in.readString();
        description = in.readString();
        openingHours = in.readString();
        phoneNumber = in.readString();
        covidCapacity = in.readInt();
    }

    public static final Creator<Barbershop> CREATOR = new Creator<Barbershop>() {
        @Override
        public Barbershop createFromParcel(Parcel in) {
            return new Barbershop(in);
        }

        @Override
        public Barbershop[] newArray(int size) {
            return new Barbershop[size];
        }
    };

    @DocumentId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("image1")
    public String getImage1() {
        return image1;
    }
    @PropertyName("image1")
    public void setImage1(String image1) {
        this.image1 = image1;
    }
    @PropertyName("image2")
    public String getImage2() {
        return image2;
    }
    @PropertyName("image2")
    public void setImage2(String image2) {
        this.image2 = image2;
    }
    @PropertyName("image3")
    public String getImage3() {
        return image3;
    }
    @PropertyName("image3")
    public void setImage3(String image3) {
        this.image3 = image3;
    }
    @PropertyName("Name")
    public String getName() {
        return name;
    }
    @PropertyName("Name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("Address")
    public String getAddress() {
        return address;
    }
    @PropertyName("Address")
    public void setAddress(String address) {
        this.address = address;
    }
    @PropertyName("Suburb")
    public String getSuburb() {
        return suburb;
    }
    @PropertyName("Suburb")
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
    @PropertyName("Prices")
    public String getPrices() {
        return prices;
    }
    @PropertyName("Prices")
    public void setPrices(String prices) {
        this.prices = prices;
    }
    @PropertyName("Description")
    public String getDescription() {
        return description;
    }
    @PropertyName("Description")
    public void setDescription(String description) {
        this.description = description;
    }
    @PropertyName("OpeningH")
    public String getOpeningHours() {
        return openingHours;
    }
    @PropertyName("OpeningH")
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
    @PropertyName("Phone")
    public String getPhone() {
        return phoneNumber;
    }
    @PropertyName("Phone")
    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @PropertyName("Covid19C")
    public int getCovidCapacity() {
        return covidCapacity;
    }
    @PropertyName("Covid19C")
    public void setCovidCapacity(int covidCapacity) {
        this.covidCapacity = covidCapacity;
    }


    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(image3);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(suburb);
        dest.writeString(prices);
        dest.writeString(description);
        dest.writeString(openingHours);
        dest.writeString(phoneNumber);
        dest.writeInt(covidCapacity);

    }
}
