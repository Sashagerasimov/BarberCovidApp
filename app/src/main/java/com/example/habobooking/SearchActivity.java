package com.example.habobooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
