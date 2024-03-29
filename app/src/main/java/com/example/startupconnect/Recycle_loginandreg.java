package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Recycle_loginandreg extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView r1;
    ArrayList<ContentRecylelogin> arr=new ArrayList<>();
    String TAG="Recyle_loginandreg";
    private FirebaseAuth auth;
    String userName="";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_loginandreg);

        r1=findViewById(R.id.recycle1);
        r1.setLayoutManager(new LinearLayoutManager(this));

        userName = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // defining the hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        //getSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, com.google.android.gms.ads.impl.R.string.native_body);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // code when navigation items are clicked
        navigationView.setNavigationItemSelectedListener(this);
        //

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

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_profile:
                // go to profile activity
                Intent profile = new Intent(Recycle_loginandreg.this, UserProfileActivity.class);
                startActivity(profile);
                break;
            case R.id.nav_complaint:
                Intent complaint = new Intent(Recycle_loginandreg.this, ComplaintActivity.class);
                startActivity(complaint);
                break;
                // go to complaints activity
            case R.id.nav_logout:
                auth=FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(Recycle_loginandreg.this, Loginandreg.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // logout the user
        }
        return true;
    }
}