package com.example.habobooking.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.habobooking.Interface.IBookingInfoLoadListener;
import com.example.habobooking.Interface.IBookingInformationChangedListener;
import com.example.habobooking.Interface.ILookbookLoadListener;
import com.example.habobooking.Model.Banner;
import com.example.habobooking.Model.Barbershop;
import com.example.habobooking.ProfileActivity;
import com.example.habobooking.Model.BookingInformation;
import com.example.habobooking.Model.User;
import com.example.habobooking.R;
import com.example.habobooking.SearchActivity;
import com.example.habobooking.Service.PicassoImageLoadingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.auth.User;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IBannerLoadListener, IBarbershopBannerLoadListener, IBookingInfoLoadListener, IBookingInformationChangedListener {

    private Unbinder unbinder;

    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_look_book)
    RecyclerView recycler_look_book;

    @BindView(R.id.card_booking_info)
    CardView card_booking_info;
    @BindView(R.id.txt_salon_address)
    TextView txt_salon_address;
    @BindView(R.id.txt_salon_barber)
    TextView txt_salon_barber;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.txt_time_remaining)
    TextView txt_time_remaining;

    AlertDialog dialog;

    @OnClick(R.id.btn_delete_booking)
    void deleteBooking()
    {
        deleteBookingFromBarber(false);
    }

    @OnClick(R.id.btn_change_booking)
    void changeBooking(){
        changeBookingFromUser();
    }

    private void changeBookingFromUser() {
        androidx.appcompat.app.AlertDialog.Builder confirmDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to change this booking.\nIt will need to be deleted and re-booked")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBookingFromBarber(true);
                    }
                });
        confirmDialog.show();
    }

    private void deleteBookingFromBarber(boolean isChanged) {
        if(Common.currentBooking != null)
        {
            dialog.show();

            DocumentReference barberBookingInfo = FirebaseFirestore.getInstance()
                    .collection("AllSalon")
                    .document(Common.currentBooking.getCityBook())
                    .collection("Branch")
                    .document(Common.currentBooking.getSalonId())
                    .collection("Barber")
                    .document(Common.currentBooking.getBarberId())
                    .collection(Common.convertTimeStampToStringKey(Common.currentBooking.getTimestamp()))
                    .document(Common.currentBooking.getSlot().toString());

            barberBookingInfo.delete().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    deleteBookingFromUser(isChanged);
                }
            });
        }
        else
        {
            Toast.makeText(getContext(),"Current Booking must not be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteBookingFromUser(boolean isChanged) {
        if(!TextUtils.isEmpty(Common.currentBookingId))
        {
            DocumentReference userBookingInfo = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(Common.currentUser.getPhoneNumber())
                    .collection("Booking")
                    .document(Common.currentBookingId);

            userBookingInfo.delete().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Paper.init(getActivity());
                    Uri eventUri = Uri.parse(Paper.book().read(Common.EVENT_URI_CACHE).toString());
                    getActivity().getContentResolver().delete(eventUri,null,null);
                    Toast.makeText(getActivity(), "Booking deleted successfully!", Toast.LENGTH_SHORT).show();

                    loadUserBooking();

                    if(isChanged)
                        iBookingInformationChangedListener.onBookingInformationChanged();

                    dialog.dismiss();
                }
            });
        }
        else
        {
            dialog.dismiss();
            
            Toast.makeText(getContext(), "Booking information and ID must not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.card_view_booking)
    void booking()
    {
        startActivity(new Intent(getActivity(), BookingActivity.class));
    }

    //firestore
    CollectionReference bannerRef, sydneyRef, strathfieldRef, parramattaRef, newtownRef, cityRef, blacktownRef;

    //interface
    IBannerLoadListener iBannerLoadListener;
    IBookingInfoLoadListener iBookingInfoLoadListener;
    IBarbershopBannerLoadListener iBarbershopLoadListener;
    IBookingInformationChangedListener iBookingInformationChangedListener;
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
    public void onResume() {
        super.onResume();
        loadUserBooking();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
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
        iBookingInfoLoadListener = this;
        iBarbershopLoadListener = this;
        iBookingInformationChangedListener = this;

        //get button
        ImageButton search = view.findViewById(R.id.searchButton);

        // check user logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            setUserInformation();
            loadUserBooking();
            loadBanner();
            loadLookBook();
            loadUserBooking();
            search.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putParcelableArrayListExtra("listOfShops", (ArrayList) barbershops);
                startActivity(intent);
            });
        }

        //Listener for the profile button
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

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

    private void loadUserBooking() {
        CollectionReference userBooking = FirebaseFirestore.getInstance()
                .collection("User")
                //.document(Common.currentUser.getPhoneNumber())
                .document("+61404040404")
                .collection("Booking");

        //get date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);

        Timestamp toDayTimeStamp = new Timestamp(calendar.getTime());

        //firebase selection
        userBooking
                .whereGreaterThanOrEqualTo("timestamp",toDayTimeStamp)
                .whereEqualTo("done",false)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {
                            if(!task.getResult().isEmpty())
                            {
                                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                                {
                                    BookingInformation bookingInformation = queryDocumentSnapshot.toObject(BookingInformation.class);
                                    iBookingInfoLoadListener.onBookingInfoLoadSuccess(bookingInformation,queryDocumentSnapshot.getId());
                                    break;
                                }
                            }
                            else
                                iBookingInfoLoadListener.onBookingInfoLoadEmpty();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBookingInfoLoadListener.onBookingInfoLoadFailed(e.getMessage());
            }
        });
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

    @Override
    public void onBookingInfoLoadEmpty() {
        card_booking_info.setVisibility(View.GONE);
    }

    @Override
    public void onBookingInfoLoadSuccess(BookingInformation bookingInformation,String bookingId) {

        Common.currentBooking = bookingInformation;
        Common.currentBookingId = bookingId;

        txt_salon_address.setText(bookingInformation.getSalonAddress());
        txt_salon_barber.setText(bookingInformation.getBarberName());
        txt_time.setText(bookingInformation.getTime());
        String dateRemain = DateUtils.getRelativeTimeSpanString(
                Long.valueOf(bookingInformation.getTimestamp().toDate().getTime()),
                Calendar.getInstance().getTimeInMillis(),0).toString();
        txt_time_remaining.setText(dateRemain);

        card_booking_info.setVisibility(View.VISIBLE);

        dialog.dismiss();
    }

    @Override
    public void onBookingInfoLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBookingInformationChanged() {
        startActivity(new Intent(getActivity(),BookingActivity.class));
    }
}
