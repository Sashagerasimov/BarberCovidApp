package com.example.habobooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habobooking.Model.Barbershop;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BarbershopprofileActivity extends AppCompatActivity {

    //setting all the connections
    CollapsingToolbarLayout collapsingToolbarLayout;

    TextView shopDescription;
    TextView shopCovid;
    TextView shopPrices;
    TextView shopAddress;
    TextView shopOpenHours;
    ImageView barbershopImage1;
    TextView shopSuburb;
    Button shopButton;
    ImageView googleIcon;

    TextView shopPhone;
    //variables
    public static String barbershopName;
    private static String barbershopDescription;
    private static double covidCapacity;
    private static String barbershopPrices;
    private static String barbershopAddress;
    private String barbershopOpenHours;
    private String barbershopImageOne;
    private static String barbershopPhone;
    private static String documentID;
    private static String barberSuburb;

    //Document



    CollectionReference barbershopListRef;
    DocumentReference barbershopProfileRef;

    //Creating list of barbershops
    List<Barbershop> barbershopList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershopprofile);

        barbershopImage1 = findViewById(R.id.barbershop_image1);
        shopCovid = findViewById(R.id.covidCapacityTxtView);
        shopPrices = findViewById(R.id.pricesTxtView);
        shopAddress = findViewById(R.id.barbershopAddressTxt);
        shopOpenHours = findViewById(R.id.openingHoursTxtView);
        collapsingToolbarLayout = findViewById(R.id.barbershop_collapse_tb);
        shopDescription = findViewById(R.id.barbershopDescription);
        shopButton = findViewById(R.id.bookNowButton);
        shopPhone = findViewById(R.id.phoneNumberTxt);
        googleIcon = findViewById(R.id.googleicon_bProfile);
        shopSuburb = findViewById(R.id.suburbBarberProfileTxtView);


        //Intent to grab the document
        //ID of the barbershop that was selected
        Intent explicitIntent = getIntent();
        documentID = explicitIntent.getStringExtra("id");
        barberSuburb = explicitIntent.getStringExtra("suburb");



        //setting defaults
        barbershopName = "HaboBooking";
       //barberSuburb = "Parramatta";
        //Grab the document id
       // documentID = "e76WkaOXw7f132wjclyb";

        //Extracting specific barbershop
        barbershopProfileRef = FirebaseFirestore.getInstance().collection("AllSalon").document(barberSuburb).collection("Branch").document(documentID);

        loadBarbershopProfile();
        //not sure what to do with this
        //iBarbershopLoadListener = this;

        //loadbarbershops();
        System.out.println("hello test");



        barbershopName = "THIS IS A TEST";
        //make this barbershop name
        collapsingToolbarLayout.setTitle(barbershopName);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        //Loading image
        //testing
       // Picasso.get().load(lookbook.get(i).getImage()).into(myViewHolder.imageView);


        //Button

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                Toast.makeText(context, "Moving to booking system", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (BarbershopprofileActivity.this, BookingActivity.class);

                startActivity(intent);

            }

        });
        googleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, barbershopName + " " + barberSuburb);
                startActivity(intent);

            }
        });



    }




    public void loadBarbershopProfile() {
        barbershopProfileRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    //extract values out of this document and show it
                    Barbershop barbershop =documentSnapshot.toObject(Barbershop.class);

                    System.out.println("Please work " + barbershop.getDescription());

                    barbershopDescription = documentSnapshot.getString("Description");
                    shopDescription.setText(barbershopDescription);
                    barbershopAddress = documentSnapshot.getString("Address");
                    shopAddress.setText(barbershopAddress);

                    collapsingToolbarLayout.setTitle(barbershopName);
                    barbershopName = documentSnapshot.getString("Name");
                    System.out.println("this is the name " + barbershopName);
                    collapsingToolbarLayout.setTitle(barbershopName);


                    barbershopOpenHours = documentSnapshot.getString("OpeningH");

                    int positionOfWeekend =  barbershopOpenHours.indexOf("Weekends");
                    String weekdays = barbershopOpenHours.substring(0,positionOfWeekend-1);
                    String weekends = barbershopOpenHours.substring(positionOfWeekend);

                    barbershopOpenHours = weekdays + "\n" + weekends;

                    shopOpenHours.setText(barbershopOpenHours);

                    barbershopPhone = documentSnapshot.getString("Phone");
                    shopPhone.setText(barbershopPhone);

                    barbershopImageOne = documentSnapshot.getString("image1");
                    Picasso.get().load(barbershopImageOne).into(barbershopImage1);

                    barbershopPrices= documentSnapshot.getString("Prices");
                    System.out.println("This is prices " + barbershopPrices);
                    int positionOfTeens = barbershopPrices.indexOf("Teen");
                    int positionOfKids = barbershopPrices.indexOf("Kid");
                    String priceAdults = barbershopPrices.substring(0,positionOfTeens-1);
                    System.out.println("this is price of adults" + priceAdults);

                    String priceTeens = barbershopPrices.substring(positionOfTeens, positionOfKids-1);
                    System.out.println("this is price of teens " + priceTeens);
                    String priceKids = barbershopPrices.substring(positionOfKids);

                    barbershopPrices = priceKids + "\n" + priceTeens + "\n" + priceAdults;
                    shopPrices.setText(barbershopPrices);

                    covidCapacity = documentSnapshot.getDouble("Covid19C");

                   int covidInteger = (int) covidCapacity;
                   String covidString = Integer.toString(covidInteger);
                   shopCovid.setText(covidString);

                    barberSuburb = documentSnapshot.getString("Suburb");
                    shopSuburb.setText(barberSuburb);


                    //barbershop



                } else {
                    //if this document ref doesnt exist
                    Toast.makeText(BarbershopprofileActivity.this, "This profile does not exist!",Toast.LENGTH_SHORT).show();

                    System.out.println("This document ref doesn't exist: " + documentID);
                    System.out.println("This is the suburb that was passed: " + barberSuburb);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
