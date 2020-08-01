package com.example.habobooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.habobooking.Adapter.SearchAdapter;
import com.example.habobooking.Model.Barbershop;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView shopsList;
    private SearchAdapter searchAdapter;
    private List<Barbershop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //get list
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        shops = extra.getParcelableArrayList("listOfShops");

        //get recyclerview widget
        shopsList = findViewById(R.id.shopsList);

        //create adapted linked to list
        searchAdapter = new SearchAdapter(this, shops);

        //set adapter to recycler view
        shopsList.setAdapter(searchAdapter);

        //set defauly layout manager
        shopsList.setLayoutManager(new LinearLayoutManager(this));


    }
}