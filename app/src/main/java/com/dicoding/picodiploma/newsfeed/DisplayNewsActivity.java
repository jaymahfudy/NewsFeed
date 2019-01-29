package com.dicoding.picodiploma.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class DisplayNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        String url;
        if (getIntent().getExtras().getString("NEWS_URL")!=null){
            url = getIntent().getExtras().getString("NEWS_URL");
            WebView wv = findViewById(R.id.wv_news);
            wv.loadUrl(url);
        }
    }
}
