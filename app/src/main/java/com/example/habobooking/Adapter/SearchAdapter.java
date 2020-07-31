package com.example.habobooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habobooking.Model.Barbershop;
import com.example.habobooking.R;

import org.w3c.dom.Text;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    List<Barbershop> shops;

    public SearchAdapter(Context context, List<Barbershop> shops) {
        this.context = context;
        this.shops = shops;
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

        }
    }
}


