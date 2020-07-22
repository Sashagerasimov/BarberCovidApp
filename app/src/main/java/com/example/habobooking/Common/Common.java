package com.example.habobooking.Common;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.habobooking.Model.Barber;
import com.example.habobooking.Model.MyToken;
import com.example.habobooking.Model.Salon;
import com.example.habobooking.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import io.paperdb.Paper;

public class Common {
    public static final String LOGGED_KEY = "UserLogged" ;
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "KEY_STEP";
    public static final String KEY_BARBER_SELECTED = "KEY_BARBER_SELECTED";
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static Salon currentSalon;
    public static int step = 0;
    public static String city = "";
    public static Barber currentBarber;

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

