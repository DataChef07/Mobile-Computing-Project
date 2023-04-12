package com.example.startupconnect;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Recycle_loginandreg extends AppCompatActivity {

    RecyclerView r1;
    ArrayList<ContentRecylelogin> arr=new ArrayList<>();
    String TAG="Recyle_loginandreg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_loginandreg);

        r1=findViewById(R.id.recycle1);
        r1.setLayoutManager(new LinearLayoutManager(this));

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
//                        HashMap
                        Log.i(TAG, "onComplete: ====>"+datasnapshot.getValue());
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
}