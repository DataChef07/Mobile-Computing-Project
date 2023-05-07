package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    TextView userName, fullName, email, rating, phone;

    private FirebaseAuth auth;
    DatabaseReference mDatabase;

    String CurrUser, currUserName, currUserEmail, currUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userName = findViewById(R.id.userName);
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.user_email);
        rating = findViewById(R.id.rating);
        phone = findViewById(R.id.phone);

        auth = FirebaseAuth.getInstance();
        CurrUser = auth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrUser);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUserName = snapshot.child("name").getValue(String.class);
                Log.i("TAG", "onDataChange: "+currUserName);
//                Toast.makeText(UserProfileActivity.this, currUserName, Toast.LENGTH_SHORT).show();
                userName.setText(currUserName);
                fullName.setText(currUserName);
                currUserPhone = snapshot.child("Phno").getValue(String.class);
                phone.setText(currUserPhone);
//                Toast.makeText(UserProfileActivity.this, currUserPhone, Toast.LENGTH_SHORT).show();
                currUserEmail = auth.getCurrentUser().getEmail();
                email.setText(currUserEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                name = snapshot.child("name").getValue(String.class);
//                Log.i("TAG", "onDataChange: "+name);
//                Toast.makeText(UserProfileActivity.this, name, Toast.LENGTH_SHORT).show();
//                userName.setText(name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };


    }
}