package com.example.habobooking.Common;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.habobooking.Model.Barber;
import com.example.habobooking.Model.MyToken;
import com.example.habobooking.Model.Salon;
import com.example.habobooking.Model.TimeSlot;
import com.example.habobooking.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;

public class Common {
    public static final String LOGGED_KEY = "UserLogged" ;
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_BARBER_SELECTED = "BARBER_SELECTED";
    public static final int TIME_SLOT_TOTAL = 20;
    public static final Object DISBALE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static Salon currentSalon;
    public static int step = 0;
    public static String city = "";
    public static Barber currentBarber;
    public static int currentTimeSlot = -1;
    public static Calendar currentDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

    public static String convertTimeSlotToString(int slot) {
        switch(slot) {
            case 0:
                return "9:00-9:30";
            case 1:
                return "9:30-10:00";
            case 2:
                return "10:00-10:30";
            case 3:
                return "10:30-11:00";
            case 4:
                return "11:00-11:30";
            case 5:
                return "11:30-12:00";
            case 6:
                return "12:00-12:30";
            case 7:
                return "12:30-13:00";
            case 8:
                return "13:00-13:30";
            case 9:
                return "13:30-14:00";
            case 10:
                return "14:00-14:30";
            case 11:
                return "14:30-15:00";
            case 12:
                return "15:00-15:30";
            case 13:
                return "15:30-16:00";
            case 14:
                return "16:00-16:30";
            case 15:
                return "16:30-17:00";
            case 16:
                return "17:00-17:30";
            case 17:
                return "17:30-18:00";
            case 18:
                return "18:00-18:30";
            case 19:
                return "18:30-19:00";
            case 20:
                return "19:00-19:30";
            default:
                return "Closed";
        }
    }

    public static enum TOKEN_TYPE{
        CLIENT,
        BARBER,
        MANAGER
    }

    public static void updateToken(Context context, final String s) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                MyToken myToken = new MyToken();
                myToken.setToken(s);
                myToken.setTokenType(TOKEN_TYPE.CLIENT);
                myToken.setUserPhone(user.getPhoneNumber());

                FirebaseFirestore.getInstance()
                        .collection("Tokens")
                        .document(user.getPhoneNumber())
                        .set(myToken)
                        .addOnCompleteListener(task -> {

                        });

            }
    else{
                Paper.init(context);
                String localUser = Paper.book().read(Common.LOGGED_KEY);
                if (localUser != null) {
                    if (!TextUtils.isEmpty(localUser)) {
                        MyToken myToken = new MyToken();
                        myToken.setToken(s);
                        myToken.setTokenType(TOKEN_TYPE.CLIENT);
                        myToken.setUserPhone(localUser);

                        FirebaseFirestore.getInstance()
                                .collection("Tokens")
                                .document(localUser)
                                .set(myToken)
                                .addOnCompleteListener(task -> {

                                });
                    }
                }
            }
        }
}

