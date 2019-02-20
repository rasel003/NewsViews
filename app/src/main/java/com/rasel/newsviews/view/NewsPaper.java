package com.rasel.newsviews.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rasel.newsviews.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsPaper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_paper);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String url;

        WebView webViewResult = findViewById(R.id.webView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);


        if (extras != null) {
            url = extras.getString("url");

            webViewResult.setWebViewClient(new WebViewClient());
            webViewResult.loadUrl(url);

            WebSettings webSettings = webViewResult.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webViewResult.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
