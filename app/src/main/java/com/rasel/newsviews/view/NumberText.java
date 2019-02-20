package com.rasel.newsviews.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rasel.newsviews.R;
import com.rasel.newsviews.api.RetrofitClient_Number;

import java.io.IOException;
import java.util.Objects;

public class NumberText extends AppCompatActivity {
    private static final String TAG = "NumberText";
    private ProgressBar progressBar;
    TextView tvNumber, tvNumberHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_text);
        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        tvNumber = findViewById(R.id.tvNumber);
        tvNumberHistory = findViewById(R.id.tvNumberHistory);
        tvNumberHistory.setVisibility(View.GONE);

        if (extras != null && extras.containsKey("number")) {
            String query = extras.getString("number", "number");
            tvNumber.setText(query);
            getTextForNumber(query);
        }
    }

    private void getTextForNumber(final String query) {
        Call<ResponseBody> call = RetrofitClient_Number.getInstance().getApi().getNumberText(query);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Number Api onResponse: Code: " + response.code());
                    return;
                }
                try {
                    if (response.body() != null) {
                        String result = response.body().string();
                        Log.d(TAG, "onResponse: Number return result : --" + result);
                        tvNumberHistory.setText(result);
                        tvNumberHistory.setVisibility(View.VISIBLE);
                    } else {
                        Log.d(TAG, "onResponse: Number api response is null");
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onResponse: Exception Occurred in uploading Profile Image" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: Google News Response" + t.getMessage());
                progressBar.setVisibility(View.GONE);
                tvNumberHistory.setVisibility(View.VISIBLE);
                Toast.makeText(NumberText.this, "Something went wrong, Try Again", Toast.LENGTH_SHORT).show();
            }
        });

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
