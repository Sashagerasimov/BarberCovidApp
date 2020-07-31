package com.example.habobooking.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habobooking.Adapter.HomeSliderAdapter;
import com.example.habobooking.Adapter.LookbookAdapter;
import com.example.habobooking.BookingActivity;
import com.example.habobooking.Common.Common;
import com.example.habobooking.HomeActivity;
import com.example.habobooking.Interface.IBannerLoadListener;
import com.example.habobooking.Interface.IBarbershopBannerLoadListener;
import com.example.habobooking.Model.Banner;
import com.example.habobooking.Model.Barbershop;
import com.example.habobooking.R;
import com.example.habobooking.SearchActivity;
import com.example.habobooking.Service.PicassoImageLoadingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IBannerLoadListener, IBarbershopBannerLoadListener {

    private Unbinder unbinder;

    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_look_book)
    RecyclerView recycler_look_book;
    @OnClick(R.id.card_view_booking)
    void booking()
    {
        startActivity(new Intent(getActivity(), BookingActivity.class));
    }

    //firestore
    CollectionReference bannerRef, sydneyRef, strathfieldRef, parramattaRef, newtownRef, cityRef, blacktownRef;

    //interface
    IBannerLoadListener iBannerLoadListener;
    IBarbershopBannerLoadListener iBarbershopLoadListener;

    //barbershop store
    List<Barbershop> barbershops = new ArrayList<>();

    public HomeFragment() {
        bannerRef = FirebaseFirestore.getInstance().collection("Banner");
        //barbershopRef = FirebaseFirestore.getInstance().collection("Lookbook");
        sydneyRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("Sydney")
                .collection("Branch");
        strathfieldRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("Strathfield")
                .collection("Branch");
        parramattaRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("Parramatta")
                .collection("Branch");
        newtownRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("Newtown")
                .collection("Branch");
        cityRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("City")
                .collection("Branch");
        blacktownRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document("Blacktown")
                .collection("Branch");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this,view);

        // initiate
        Slider.init(new PicassoImageLoadingService());
        iBannerLoadListener = this;
        iBarbershopLoadListener = this;

        //get button
        ImageButton search = view.findViewById(R.id.searchButton);

        // check user logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            setUserInformation();
            loadBanner();
            loadLookBook();
            search.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putParcelableArrayListExtra("listOfShops", (ArrayList) barbershops);
                startActivity(intent);
            });
        }

        return view;
    }

    private void loadLookBook() {
        //loadCityStores(sydneyRef);
        loadCityStores(strathfieldRef);
        loadCityStores(parramattaRef);
        loadCityStores(newtownRef);
        loadCityStores(cityRef);
        loadCityStores(blacktownRef);
    }

    private void loadCityStores(CollectionReference ref){
        ref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot shop:task.getResult())
                            {
                                Barbershop barbershop = shop.toObject(Barbershop.class);
                                System.out.println(barbershop.getImage1());
                                System.out.println(barbershop.getId());
                                System.out.println("------------------------------------------" + barbershop.getName());
                                System.out.println("------------------------------------------" + barbershop.getDescription());
                                System.out.println("------------------------------------------" + barbershop.getPhone());
                                System.out.println("------------------------------------------" + barbershop.getAddress());
                                System.out.println("------------------------------------------" + barbershop.getPrices());
                                System.out.println("------------------------------------------" + barbershop.getOpeningHours());
                                System.out.println("------------------------------------------" + barbershop.getSuburb());
                                System.out.println("------------------------------------------" + barbershop.getCovidCapacity());
                                System.out.println("------------------------------------------" + barbershop.getImage1());
                                System.out.println("------------------------------------------" + barbershop.getImage2());
                                System.out.println("------------------------------------------" + barbershop.getImage2());

                                barbershops.add(barbershop);


                                // Banner banners = shop.toObject(Banner.class);
                                // barbershops.add(banners);

                            }
                            iBarbershopLoadListener.onBarbershopBannerLoadSuccess(barbershops);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBarbershopLoadListener.onBarbershopBannerLoadFailed(e.getMessage());
            }
        });
    }

    private void loadBanner() {
        bannerRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Banner> banners = new ArrayList<>();
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot bannerSnapShot:task.getResult())
                            {
                                Banner banner = bannerSnapShot.toObject(Banner.class);
                                banners.add(banner);
                            }
                            iBannerLoadListener.onBannerLoadSuccess(banners);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               iBannerLoadListener.onBannerLoadFailed(e.getMessage());
            }
        });
    }

    private void setUserInformation() {
        layout_user_information.setVisibility(View.VISIBLE);
        txt_user_name.setText(Common.currentUser.getName());
    }

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {
        banner_slider.setAdapter(new HomeSliderAdapter(banners));
    }

    @Override
    public void onBannerLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBarbershopBannerLoadSuccess(List<Barbershop> banners) {
        recycler_look_book.setHasFixedSize(true);
        recycler_look_book.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_look_book.setAdapter(new LookbookAdapter(getActivity(),banners));
    }

    @Override
    public void onBarbershopBannerLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
