package com.example.startupconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity2 extends AppCompatActivity {

    private Button logout, refreshQR;
    private ImageView QRimage;
    private TextView t1;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private FirebaseAuth auth;
    private String sport="";
    private String QR_String="";
    String userName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        sport = intent.getStringExtra(AdapterRecycleLogin.EXTRA_SPORT);

        logout = findViewById(R.id.logout);
        refreshQR = findViewById(R.id.refreshQRbutton);
        QRimage = findViewById(R.id.QRimage);
//        t1=findViewById(R.id.textView);

//        sport=intent.getStringExtra("sport");
//        t1.setText(sport);
        setImage();

        auth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(MainActivity2.this, Loginandreg.class);
                startActivity(intent);
                finish();
            }
        });

        refreshQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//                Display display = windowManager.getDefaultDisplay();
//                Point point = new Point();
//                display.getSize(point);
//                int width = point.x;
//                int height = point.y;
//                int dim = height;
//                if(width<height)
//                    dim = width;
//
//                dim = dim * 3/4;
//
//                qrgEncoder = new QRGEncoder(userName, null, QRGContents.Type.TEXT, dim);
//                try{
//                    bitmap = qrgEncoder.getBitmap();
//                    QRimage.setImageBitmap(bitmap);
//                }
//                catch (Exception e){
//                    Log.d("QRCODE", e.toString());
//                }
                setImage();
                Toast.makeText(MainActivity2.this, "welcome:  " + QR_String, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setImage() {
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
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new java.util.Date());
        QR_String = userName + "/////"+ sport +"/////"+ timeStamp;
        qrgEncoder = new QRGEncoder(QR_String, null, QRGContents.Type.TEXT, dim);
        try{
            bitmap = qrgEncoder.getBitmap();
            QRimage.setImageBitmap(bitmap);
        }
        catch (Exception e){
            Log.d("QRCODE", e.toString());
        }
    }
}