package com.example.habobooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText profileName, profileAddress, profileNumber;
    private Button updateBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    String DISPLAY_NAME = null;
    CollectionReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.etProfileName);
        profileAddress = findViewById(R.id.etProfileAddress);
        profileNumber = findViewById(R.id.etProfilePhoneNumber);
        updateBtn = findViewById(R.id.updateProfileBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.child("name").setValue("bill");

        profileName.setText(Common.currentUser.getName());
        profileAddress.setText(Common.currentUser.getAddress());
        profileNumber.setText(Common.currentUser.getPhoneNumber());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = profileName.getText().toString().trim();
                String address = profileAddress.getText().toString().trim();
                String phone = profileNumber.getText().toString().trim();

                updateUser("name", phone, address);
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

    private boolean updateUser(String name, String phone, String address){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(phone);

        User user = new User(name, phone, address);

        databaseReference.setValue(user);

        Toast.makeText(this, "User Updated Succesfully", Toast.LENGTH_LONG).show();

        return true;
    }

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