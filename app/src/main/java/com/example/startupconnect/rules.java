package com.example.startupconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

public class rules extends AppCompatActivity {

    Hashtable<String, String> sport_url = new Hashtable<>();
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Intent intent = getIntent();
        String sport = intent.getStringExtra(MainActivity2.EXTRA_SPORT);

        sport_url.put("Badminton", "https://olympics.com/en/news/badminton-guide-how-to-play-rules-olympic-history");
//        sport_url.put("Table tennis","https://docs.google.com/viewer?embedded=true&url=" + "https://crec.unl.edu/im/rules/Table_Tennis.pdf");
        sport_url.put("Table tennis","https://www.experttabletennis.com/table-tennis-rules-and-regulations/");
        sport_url.put("Gym", "https://docs.google.com/viewer?embedded=true&url="+"https://www.utar.edu.my/media/DSA/2015/GYMrules.pdf");
        sport_url.put("Squash", "https://docs.google.com/viewer?embedded=true&url="+"https://irp.cdn-website.com/5755cba0/files/uploaded/Squash%20Start%20Manual_compressed.pdf");
        sport_url.put("Air hockey", "https://docs.google.com/viewer?embedded=true&url="+"https://www.olhausenbilliards.com/wp-content/uploads/2020/04/Resource-Page-Air-Hockey-Rules.pdf");
        sport_url.put("8 ball Pool", "https://docs.google.com/viewer?embedded=true&url="+"https://www.colorado.edu/umc/sites/default/files/attached-files/8-ball_rules_bca.pdf");
        sport_url.put("Swimming", "https://docs.google.com/viewer?embedded=true&url="+"https://www.teamunify.com/pnupac/__doc__/246755_2_BASICSWIMMINGRULES.pdf");
        sport_url.put("Foos ball",  "https://docs.google.com/viewer?embedded=true&url="+"https://www.rentalmethis.com/documents/Foosball%20Rules.pdf");

        webView = findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(sport_url.get(sport));

    }
}