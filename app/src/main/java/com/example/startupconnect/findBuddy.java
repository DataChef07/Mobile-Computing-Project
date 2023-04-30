package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class findBuddy extends AppCompatActivity {
    TextView available_members, matched;
    Button confirm;
    CheckBox intrested_checkbox;
    String name;
    ArrayList<String> UserID_list= new ArrayList<>();
    ArrayList<String> name_list= new ArrayList<>();
    ArrayList<Boolean> intrested_list= new ArrayList<>();
    ArrayList<Boolean> matched_list= new ArrayList<>();
    ArrayList<String> Rating_list= new ArrayList<>();
    ArrayList<String> Sport_list= new ArrayList<>();

    ArrayList<Integer> matched_index = new ArrayList<>();
    String MatchedUser = "";
    String CurrUser;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy2);

        available_members = findViewById(R.id.disp);
        intrested_checkbox = findViewById(R.id.intrested);
        confirm = findViewById(R.id.confirm);
        matched = findViewById(R.id.matched);
        auth = FirebaseAuth.getInstance();
        CurrUser = auth.getCurrentUser().getUid();
        Log.d("userdetail", "detail ===>  " + CurrUser);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrUser).child("intrested");

        intrested_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(intrested_checkbox.isChecked()){
                    mDatabase.setValue(true);
                    fetchDetails();
                    int count = countIntrested(CurrUser);
                    Log.d("findBuddy", "matched_index ===>   " + matched_index);

                    if(count > 0)
                        MatchedUser = searchForMatches(CurrUser, matched_index);
                    available_members.setText("Members available ===>  " + String.valueOf(count));
                    if(!MatchedUser.equals("")){
                        matched.setText("Matched with ====>    " + MatchedUser);
                    }

                    Log.d("matchedUser", "matcheduser ===>  " + MatchedUser);
                }
                if(!intrested_checkbox.isChecked()){
                    mDatabase.setValue(false);
                    available_members.setText("");
                }
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intrested_checkbox.setChecked(false);
                intrested_checkbox.setEnabled(false);
                DatabaseReference obj = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrUser);
                obj.child("intrested").setValue(false);
                obj.child("matched").setValue(true);
                obj = FirebaseDatabase.getInstance().getReference().child("Users").child(MatchedUser);
                obj.child("intrested").setValue(false);
                obj.child("matched").setValue(true);
            }
        });

    }

    private String searchForMatches(String currUser, ArrayList<Integer> matched_index) {

        int min = Integer.MAX_VALUE;
        int iterative_match = 0;
        int currRating = Integer.parseInt(Rating_list.get(UserID_list.indexOf(currUser)));
        for(int i: matched_index){
            int temp = Integer.parseInt(Rating_list.get(i));
            if(Math.abs(currRating-temp) < min){
                min = Math.abs(currRating-temp);
                iterative_match = i;
            }
        }
        return UserID_list.get(iterative_match);
    }

    private int countIntrested(String currUser) {
        int currIndex = UserID_list.indexOf(currUser);
        int len = name_list.size();
        int count =0;
        matched_index.clear();
        for(int i = 0; i<len; i++){
            if(intrested_list.get(i)){
                matched_index.add(i);
                count++;
            }
        }
        matched_index.remove((Integer) matched_index.indexOf(currIndex));
        return count -1;
    }

    private void fetchDetails() {
        DatabaseReference userlist = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, HashMap> temp;
                Map<String, HashMap> fetchedData = (HashMap<String, HashMap>) snapshot.getValue();
                UserID_list.clear();
                intrested_list.clear();
                matched_list.clear();
                name_list.clear();
                Sport_list.clear();
                Rating_list.clear();

                for (Map.Entry<String, HashMap> entry : fetchedData.entrySet()){
                    String key = entry.getKey();
                    UserID_list.add(key);
                    temp = entry.getValue();
                    Log.d("val check", "values ===>   "+ temp.values());

                    Object[] i= temp.values().toArray();
                    intrested_list.add((Boolean) i[0]);
                    matched_list.add((Boolean) i[1]);
                    name_list.add((String) i[3]);
                    Rating_list.add(String.valueOf(i[4]));
                    Log.d("temp", "val ===>   " + i[3]);

                }
                Log.d("findBuddy", "UserID: ===> " + UserID_list);
                Log.d("findBuddy", "name: ===> " + name_list);
                Log.d("findBuddy", "interested: ===> " + intrested_list);
                Log.d("findBuddy", "matched: ===> " + matched_list);
                Log.d("findBuddy", "Rating: ===> " + Rating_list);
                Log.d("findBuddy", "Sport: ===> " + Sport_list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(findBuddy.this, "Cant fetch data", Toast.LENGTH_SHORT).show();
            }
        };
        userlist.addListenerForSingleValueEvent(eventListener);

    }
}