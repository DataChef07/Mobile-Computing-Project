package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class findBuddy extends AppCompatActivity {
    TextView available_members, matched, msg, courts;
    Button confirm, search;
    CheckBox intrested_checkbox;
    String name;
    ArrayList<String> UserID_list= new ArrayList<>();
    ArrayList<String> name_list= new ArrayList<>();
    ArrayList<Boolean> intrested_list= new ArrayList<>();
    ArrayList<Boolean> signedin_list= new ArrayList<>();
    ArrayList<Boolean> matched_list= new ArrayList<>();
    ArrayList<String> Rating_list= new ArrayList<>();
    ArrayList<String> Sport_list= new ArrayList<>();

    ArrayList<Integer> matched_index = new ArrayList<>();
    String MatchedUser = "", matchedName;
    String CurrUser;
    String sport="";
    int[] court = {0};

    Handler handler = new Handler();
    Runnable runnable;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy2);

        Intent intent = getIntent();
        sport = intent.getStringExtra(MainActivity2.EXTRA_SPORT);
        available_members = findViewById(R.id.disp);
        intrested_checkbox = findViewById(R.id.intrested);
        confirm = findViewById(R.id.confirm);
        matched = findViewById(R.id.matched);
//        courts = findViewById(R.id.courts);
        auth = FirebaseAuth.getInstance();
        search = findViewById(R.id.search);
        msg = findViewById(R.id.msg);
        CurrUser = auth.getCurrentUser().getUid();
        Log.d("userdetail", "detail ===>  " + CurrUser);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrUser);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intrested_checkbox.setChecked(true);
                intrested_checkbox.setChecked(false);
                intrested_checkbox.setChecked(true);
                intrested_checkbox.setChecked(false);
                intrested_checkbox.setChecked(true);
                intrested_checkbox.setChecked(false);
                intrested_checkbox.setChecked(true);
            }
        });
        intrested_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(intrested_checkbox.isChecked()){
                    mDatabase.child("sport").setValue(sport);
                    mDatabase.child("intrested").setValue(true);
                    fetchDetails();
                    int count = countIntrested(CurrUser);
                    Log.d("findBuddy", "matched_index ===>   " + matched_index);

                    if(count > 0)
                        MatchedUser = searchForMatches(CurrUser, matched_index);
                    available_members.setText("Members available: " + String.valueOf(count));
                    if(!MatchedUser.equals("")){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(MatchedUser);
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                matchedName = snapshot.child("name").getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        matched.setText(matchedName + " is also searching for a buddy. Want to connect?");
                        confirm.setVisibility(View.VISIBLE);
                    }

                    Log.d("matchedUser", "matcheduser ===>  " + MatchedUser);

//                    HashMap<String, String> mp = new HashMap<>();
//                    mp.put("Air hockey", "airhockey");
//                    mp.put("Badminton", "badminton");
//                    mp.put("Football", "football");
//                    mp.put("Squash", "squash");
//                    mp.put("Table tennis", "tabletennis");
//                    mp.put("Tennis", "tennis");
//                    mp.put("swimming", "swimming");
//
//                    int people = 0;
//                    for(int i=0; i<Sport_list.size(); i++){
//                        if(Sport_list.get(i).equals(sport) && signedin_list.get(i)){
//                            people++;
//                        }
//                    }
//                    int occupied = people/4;
//                    DatabaseReference obj1 = FirebaseDatabase.getInstance().getReference();
//                    obj1.child("test").child(mp.get(sport)).child("courts").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            court[0] = Integer.parseInt(snapshot.getValue(String.class));
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    int rem = 0;
//                    if(court[0] - occupied >0){
//                        rem = court[0] - occupied;
//                        Log.d("court", "court:   ===>  " + rem);
//                    }
//                    courts.setText("Courts Available : "+String.valueOf(rem));
                }
                else{
                    mDatabase.child("sport").setValue("");
                    mDatabase.child("intrested").setValue(false);
                    available_members.setText("");
                }
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(runnable = new Runnable(){

                    @Override
                    public void run() {
                        handler.postDelayed(runnable, 3000);

                        intrested_checkbox.setChecked(false);
                        intrested_checkbox.setEnabled(false);

                        DatabaseReference obj = FirebaseDatabase.getInstance().getReference().child("Users");
                        obj.child(CurrUser).child("intrested").setValue(false);
                        obj.child(CurrUser).child("matched").setValue(true);

                        String[] time = {""};
                        obj.child(MatchedUser).child("timer")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        time[0] = snapshot.getValue(String.class);
                                        Log.d("time", "time[0] ===>  " + time[0]);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                        obj.child(MatchedUser).child("matched").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Boolean matchedval = snapshot.getValue(Boolean.class);
                                if(matchedval){
                                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date());
                                    obj.child(CurrUser).child("timer").setValue(timeStamp);

                                    matched.setText("Successfully matched with "+matchedName);
                                    msg.setVisibility(View.VISIBLE);


                                }
                                else {
                                    msg.setText("Waiting for your partner to confirm. ");
                                    msg.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }, 3000);

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
            if(sport.equals(Sport_list.get(i)) && intrested_list.get(i)){
                matched_index.add(i);
                count++;
            }
        }
        matched_index.remove((Integer) currIndex);

        return count;
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
                signedin_list.clear();

                for (Map.Entry<String, HashMap> entry : fetchedData.entrySet()){
                    String key = entry.getKey();
                    UserID_list.add(key);
                    Log.i("TAG", "Key: "+ key);
                    temp = entry.getValue();
                    Log.d("val check", "values ===>   "+ temp.values());

                    Object[] i= temp.values().toArray();
                    Log.i("TAG", "onDataChange: i");

                    // i[0] == interested
                    // i[1] == timestamp
                    //i[2] == signedIn
                    //i[3] == name
                    //i[4] == rating
                    // i[5] == roll no
                    //i[6] == equipment
                    //i[7] == matched
                    //i[8] == phone
                    //i[9] == profilePicUrl
                    //i[10] == sport
                    signedin_list.add((Boolean) i[2]);
                    intrested_list.add((Boolean) i[0]);
                    Sport_list.add((String) i[10]);
                    matched_list.add((Boolean) i[7]);
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
                Log.d("findBuddy", "signin: ===> " + signedin_list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(findBuddy.this, "Cant fetch data", Toast.LENGTH_SHORT).show();
            }
        };
        userlist.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}