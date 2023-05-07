package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity2 extends AppCompatActivity {

//    private Button logout, refreshQR, complaint, rules, find;
    private ImageView QRimage;
    private TextView t1;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private FirebaseAuth auth;
    private String sport="";
    private String QR_String="";
    String userName ="";
    public static final String EXTRA_SPORT="com.example.startupconnect.example.EXTRA_SPORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        userName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sport = intent.getStringExtra(AdapterRecycleLogin.EXTRA_SPORT);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.refreshQRbutton:
                        setImage(userName);
                        Toast.makeText(MainActivity2.this, "welcome:  " + QR_String, Toast.LENGTH_SHORT).show();
                        // Handle home item selection
                        break;
                    case R.id.Find:
                        Intent intent = new Intent(MainActivity2.this, findBuddy.class);
                        intent.putExtra(EXTRA_SPORT,sport);
                        startActivity(intent);
                        finish();
                        // Handle profile item selection
                        break;
                    case R.id.rules:
                        Intent intent1 = new Intent(MainActivity2.this, rules.class);
                        intent1.putExtra(EXTRA_SPORT,sport);
                        startActivity(intent1);
                        finish();
                        // Handle settings item selection
                        break;
                }
                return true;
            }
        });


//        logout = findViewById(R.id.logout);

        QRimage = findViewById(R.id.QRimage);
//        complaint = findViewById(R.id.complaint);
//        rules = findViewById(R.id.rules);
//        find = findViewById(R.id.Find);
        t1=findViewById(R.id.textView);

        t1.setText(sport);
        setImage(userName);


//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth=FirebaseAuth.getInstance();
//                auth.signOut();
//                Intent intent = new Intent(MainActivity2.this, Loginandreg.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        refreshQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                setImage();
//                Toast.makeText(MainActivity2.this, "welcome:  " + QR_String, Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        complaint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, ComplaintActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        rules.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, rules.class);
//                intent.putExtra(EXTRA_SPORT,sport);
//                startActivity(intent);
//                finish();
//            }
//        });

//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, findBuddy.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void setImage(String userName) {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dim = height;
        if(width<height)
            dim = width;

        dim = dim * 3/4;
        Intent intent = getIntent();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date());
        QR_String = userName + "--"+ sport +"--"+ timeStamp;
        qrgEncoder = new QRGEncoder(QR_String, null, QRGContents.Type.TEXT, dim);
        try{
            bitmap = qrgEncoder.getBitmap();
            QRimage.setImageBitmap(bitmap);
            Log.d("QrCode","Success");
            Log.d("QR TExt", "QRTEXT ===>  " + QR_String);
        }
        catch (Exception e){
            Log.d("QRCODE", e.toString());
        }
    }
}