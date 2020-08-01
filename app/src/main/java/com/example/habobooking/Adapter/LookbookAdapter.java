package com.example.habobooking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habobooking.BarbershopprofileActivity;
import com.example.habobooking.BookingActivity;
import com.example.habobooking.Model.Banner;
import com.example.habobooking.Model.Barbershop;
import com.example.habobooking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LookbookAdapter extends RecyclerView.Adapter<LookbookAdapter.MyViewHolder> {

    Context context;
    List<Barbershop> shops;

    public LookbookAdapter(Context context, List<Barbershop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_look_book,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(shops.get(i).getImage3()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.image_look_book);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("LookbookAdapter", "Button Clicked");
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
