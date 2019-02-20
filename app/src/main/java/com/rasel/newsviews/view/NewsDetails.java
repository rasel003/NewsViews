package com.rasel.newsviews.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
            Articles article = extras.getParcelable("article");

            if(article != null) {

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
