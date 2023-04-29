package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class findBuddy extends AppCompatActivity {
    TextView textbox;
    String name;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy2);

        textbox = findViewById(R.id.disp);

        auth = FirebaseAuth.getInstance();
        DatabaseReference userlist = FirebaseDatabase.getInstance().getReference().child("users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String interested = ds.child("intrested").getValue(String.class);
                    if(interested.equals("false")){
                        name = ds.child("name").getValue(String.class);
                    }
                    Log.d("findBuddy", name);

//                    array.add(name);

                }
//                ArrayAdapter<String> adapter = new ArrayAdapter(OtherUsersActivity.this, android.R.layout.simple_list_item_1, array);

//                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(findBuddy.this, "Cant fetch data", Toast.LENGTH_SHORT).show();
            }
        };
        userlist.addListenerForSingleValueEvent(eventListener);

    }
}