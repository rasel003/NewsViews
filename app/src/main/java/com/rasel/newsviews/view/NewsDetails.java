package com.rasel.newsviews.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasel.newsviews.R;
import com.rasel.newsviews.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class NewsDetails extends AppCompatActivity {
    private TextView tvNewsTitle, tvNewsAuthor, tvNewsTime, tvNewsFullStory;
    private ImageView imgNewsPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imgNewsPicture = findViewById(R.id.imgNewsPicture);
        tvNewsTitle = findViewById(R.id.tvNewsTitle);
        tvNewsAuthor = findViewById(R.id.tvNewsAuthorValue);
        tvNewsTime = findViewById(R.id.tvNewsTime);
        tvNewsFullStory = findViewById(R.id.tvNewsFullStory);


        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("article")) {
            final Articles article = extras.getParcelable("article");

            if(article != null) {
                if(article.getUrl() != null && !article.getUrl().trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("")
                            .setMessage("Load content in a browser")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl())));
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), NewsPaper.class);
                            intent.putExtra("url", article.getUrl());
                            startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                tvNewsTitle.setText(article.getTitle());
                tvNewsAuthor.setText(article.getAuthor());
                tvNewsTime.setText(article.getPublishedAt());
                tvNewsFullStory.setText(article.getContent());

                String profilepicLink = article.getUrlToImage();

                Picasso.get().load(profilepicLink)
                        .fit().centerCrop()
                        .into(imgNewsPicture);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
