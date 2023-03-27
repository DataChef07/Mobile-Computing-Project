package com.example.startupconnect;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;

public class Recycle_loginandreg extends AppCompatActivity {

    RecyclerView r1;
    ArrayList<ContentRecylelogin> arr=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_loginandreg);

        r1=findViewById(R.id.recycle1);
        r1.setLayoutManager(new LinearLayoutManager(this));
        arr.add(new ContentRecylelogin("Badminton"));
        arr.add(new ContentRecylelogin("Table tennis"));
        arr.add(new ContentRecylelogin("Gym"));
        arr.add(new ContentRecylelogin("Squash"));
        arr.add(new ContentRecylelogin("Air hockey"));
        arr.add(new ContentRecylelogin("8 ball Pool"));
        arr.add(new ContentRecylelogin("Swimming"));
        arr.add(new ContentRecylelogin("Foos ball"));






        AdapterRecycleLogin adapter=new AdapterRecycleLogin(this,arr);
        r1.setAdapter(adapter);
    }
}