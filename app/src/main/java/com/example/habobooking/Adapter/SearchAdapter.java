package com.example.habobooking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habobooking.BarbershopprofileActivity;
import com.example.habobooking.Model.Barbershop;
import com.example.habobooking.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Barbershop> shops;
    List<Barbershop> shopsFullList;

    public SearchAdapter(Context context, List<Barbershop> shops) {
        this.context = context;
        this.shops = shops;
        shopsFullList = new ArrayList<>(shops);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int item) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_search_barbershop,parent,false);
        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        Barbershop mCurrent = shops.get(position);

        String name = mCurrent.getName();

        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    @Override
    public Filter getFilter() {
        return shopsFilter;
    }

    private Filter shopsFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Barbershop> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(shopsFullList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Barbershop shop : shopsFullList){
                    if (shop.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(shop);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            shops.clear();
            shops.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView name;
        final SearchAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView, SearchAdapter adapter) {
             super(itemView);
             name = itemView.findViewById(R.id.name);
             this.mAdapter = adapter;
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("SearchAdapter", "Button Clicked");
            System.out.println("Clicked on a barbershop");
            int position = getLayoutPosition();

            Barbershop shop = shops.get(position);
            String key = shop.getId();
            String suburb = shop.getSuburb();

            Intent intent = new Intent(view.getContext(), BarbershopprofileActivity.class);
            intent.putExtra("id", key);
            intent.putExtra("suburb", suburb);
            //System.out.println("0000000000000000000000000000000000000000000000------" + key);
            view.getContext().startActivity(intent);
        }
    }
}


