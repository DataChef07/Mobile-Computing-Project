package com.example.startupconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    String category, subcategory;
    private EditText description;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        description = findViewById(R.id.concren);
        submit = findViewById(R.id.submit);

        auth = FirebaseAuth.getInstance();

        Spinner spinner = findViewById(R.id.spinner);   //get spinner details
        String[] Sport = {"Select","Badminton", "Table Tennis", "Gym", "Squash", "Air Hockey", "8 ball pool", "Swimming", "Foos Ball"};

        //populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComplaintActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Sport);

        //Style the dropdown layout
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        //attaching adapter to spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select")){
                    //do nothing ===> this will display default value on our spinner
                }
                else {
                    category = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(ComplaintActivity.this, category, Toast.LENGTH_SHORT).show();
                    Log.i("ComplaintActivity", "Sport: " + category);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner subcatspinner = findViewById(R.id.subcatspinner);   //get spinner details
        String[] subcat = {"Select", "Equipment", "Repair", "Misuse"};

        //populate the spinner
        ArrayAdapter<String> subcatadapter = new ArrayAdapter<String>(ComplaintActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, subcat);

        //Style the dropdown layout
        subcatadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        //attaching adapter to spinner
        subcatspinner.setAdapter(subcatadapter);

        subcatspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select")){
                    //do nothing ===> this will display default value on our spinner
                }
                else {
                    subcategory = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(ComplaintActivity.this, subcategory, Toast.LENGTH_SHORT).show();
                    Log.i("ComplaintActivity", "subcategory: " + subcategory);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FirebaseUser user = auth.getCurrentUser();
        String id = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ComplaintText = description.getText().toString();


            }
        });

    }
}