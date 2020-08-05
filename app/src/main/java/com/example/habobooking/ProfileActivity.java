package com.example.habobooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.sip.SipSession;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.habobooking.Common.Common;
import com.example.habobooking.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "";
    private TextInputEditText profileName, profileAddress, profileNumber;
    private Button updateBtn;
    private static final String KEY_PROFILENAME = "name";
    private static final String KEY_PROFILENUMBER = "phoneNumber";
    private static final String KEY_PROFILEADDRESS = "address";
    String DISPLAY_NAME = null;
    CollectionReference userRef;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference documentReference = db.collection("User").document(Common.currentUser.getPhoneNumber());

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e !=  null){
                    Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onEvent: "+ e.toString());
                    return;
                }

                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString(KEY_PROFILENAME);
                    String address = documentSnapshot.getString(KEY_PROFILEADDRESS);
                    String number = documentSnapshot.getString(KEY_PROFILENUMBER);

                    profileName.setText(name);
                    profileAddress.setText(address);
                    profileNumber.setText(number);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.etProfileName);
        profileAddress = findViewById(R.id.etProfileAddress);
        profileNumber = findViewById(R.id.etProfilePhoneNumber);
        updateBtn = findViewById(R.id.updateProfileBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(view);
               //String name = profileName.getText().toString().trim();
               //String address = profileAddress.getText().toString().trim();
               //String phone = profileNumber.getText().toString().trim();

               //updateUser("name", phone, address);
            }
        });

       //databaseReference.addValueEventListener(new ValueEventListener() {
       //    @Override
       //    public void onDataChange(@NonNull DataSnapshot snapshot) {
       //        Log.d("USERTAG", "user's name is " +Common.currentUser.getName());

       //        User user = snapshot.getValue(User.class);
       //        profileName.setText(Common.currentUser.getName());
       //        profileAddress.setText(Common.currentUser.getAddress());
       //        profileNumber.setText(Common.currentUser.getName());
       //        Log.d("USERTAG", "user's name is " +Common.currentUser.getName());

       //    }

       //    @Override
       //    public void onCancelled(@NonNull DatabaseError error) {
       //        Log.d("USERTAG", "user's name is " +Common.currentUser.getName());
       //        Log.d("USERTAG", "user's address is " +Common.currentUser.getAddress());
       //        Log.d("USERTAG", "user's phone is " +Common.currentUser.getPhoneNumber());

       //        Toast.makeText(ProfileActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
       //    }
       //});
    }

    public void updateUser(View v){
        String name = profileName.getText().toString();
        String address = profileAddress.getText().toString();

        Map<String, Object> profile = new HashMap<>();
        profile.put(KEY_PROFILENAME, name);
        profile.put(KEY_PROFILEADDRESS, address);

        documentReference.update(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Details saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });;


    }

    public void saveUser(View v){
        String name = profileName.getText().toString();
        String address = profileAddress.getText().toString();
        String number = profileNumber.getText().toString();

        Map<String, Object> profile = new HashMap<>();
        profile.put(KEY_PROFILENAME, name);
        profile.put(KEY_PROFILEADDRESS, address);
        profile.put(KEY_PROFILENUMBER, number);

        documentReference.set(profile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void loadUser(View v){
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String name = documentSnapshot.getString(KEY_PROFILENAME);
                            String address = documentSnapshot.getString(KEY_PROFILEADDRESS);
                            String number = documentSnapshot.getString(KEY_PROFILENUMBER);

                            profileName.setText(name);
                            profileAddress.setText(address);
                            profileNumber.setText(number);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
   // private boolean updateUser(String name, String phone, String address){
   //     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(phone);
//
   //     User user = new User(name, phone, address);
//
   //     databaseReference.setValue(user);
//
   //     Toast.makeText(this, "User Updated Succesfully", Toast.LENGTH_LONG).show();
//
   //     return true;
   // }

   // public void updateProfile(View view){
   //     DISPLAY_NAME = profileName.getText().toString();
//
   //     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
   //     UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
   //             .setDisplayName(DISPLAY_NAME)
   //             .build();
//
   //     user.updateProfile(request)
   //             .addOnSuccessListener(new OnSuccessListener<Void>() {
   //                 @Override
   //                 public void onSuccess(Void aVoid) {
   //                     Toast.makeText(ProfileActivity.this, "Successfully updated profile", Toast.LENGTH_SHORT).show();
   //                 }
   //             })
   //             .addOnFailureListener(new OnFailureListener() {
   //                 @Override
   //                 public void onFailure(@NonNull Exception e) {
   //                     Log.e("TAG", "onFailure: ", e.getCause());
   //                 }
   //             });
   // }
}