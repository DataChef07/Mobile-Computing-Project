package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Recycle_loginandreg extends AppCompatActivity {

    RecyclerView r1;
    ArrayList<ContentRecylelogin> arr=new ArrayList<>();
    String TAG="Recyle_loginandreg";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_loginandreg);

        r1=findViewById(R.id.recycle1);
        r1.setLayoutManager(new LinearLayoutManager(this));

        // defining the hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //getSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, com.google.android.gms.ads.impl.R.string.native_body);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // code when navigation items are clicked

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("sports");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isComplete())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot datasnapshot=task.getResult();

//                       firebase f=datasnapshot.getValue(firebase.class);
                        String s=datasnapshot.getKey();
//                        HashMap
                        Log.i(TAG, "onComplete: ====>"+datasnapshot.getValue().getClass().getName());
//                        Log.i(TAG, "onComplete1: ====>"+f.key);
//                        Log.i(TAG, "onComplete1: ====>"+value);
                    }
                    else
                    {

                    }
                }
                else
                {

                }
            }
        });

        arr.add(new ContentRecylelogin("Badminton",R.drawable.badminton));
        arr.add(new ContentRecylelogin("Table tennis",R.drawable.table_tennis));
        arr.add(new ContentRecylelogin("Gym",R.drawable.gym));
        arr.add(new ContentRecylelogin("Squash",R.drawable.squash));
        arr.add(new ContentRecylelogin("Air hockey",R.drawable.air_hockey));
        arr.add(new ContentRecylelogin("8 ball Pool",R.drawable.ball_pool));
        arr.add(new ContentRecylelogin("Swimming",R.drawable.swimming));
        arr.add(new ContentRecylelogin("Foos ball",R.drawable.foosball));






        AdapterRecycleLogin adapter=new AdapterRecycleLogin(this,arr);
        r1.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}