package com.rasel.newsviews.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rasel.newsviews.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*ImageView imageView = findViewById(R.id.splash_imageView);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        imageView.startAnimation(animation);*/

        SharedPreferences prefsCheck = PreferenceManager.getDefaultSharedPreferences(this);
        String firsttime = prefsCheck.getString("firsttime", "Empty");

        if (firsttime != null && firsttime.equals("true")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().putString("firsttime", String.valueOf("true")).apply();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(getApplicationContext(), SplashScreenTwo.class);
                        startActivity(intent);
                    }
                }
            };
            thread.start();
        }
    }

    public void onStop() {
        super.onStop();
        finish();
    }
}
