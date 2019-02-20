package com.rasel.newsviews.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.rasel.newsviews.adapter.ExpandableListAdapter;
import com.rasel.newsviews.adapter.GoogleNewsAdapterReverseOrder;
import com.rasel.newsviews.api.RetrofitClient_Number;
import com.rasel.newsviews.model.ExpandedMenuModel;
import com.rasel.newsviews.R;
import com.rasel.newsviews.adapter.GoogleNewsAdapter;
import com.rasel.newsviews.api.RetrofitClient;
import com.rasel.newsviews.model.Articles;
import com.rasel.newsviews.model.GoogleNewsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;

    private ExpandableListAdapter mMenuAdapter;
    private ExpandableListView expandableList;
    private List<ExpandedMenuModel> listDataHeader;
    private HashMap<ExpandedMenuModel, List<String>> listDataChild;

    private RecyclerView recyclerView, recyclerViewTwo;
    private ProgressBar progressBar, progressBarTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBarTwo = findViewById(R.id.progressBarTwo);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewTwo = findViewById(R.id.recyclerViewTwo);
        recyclerViewTwo.setHasFixedSize(true);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mDrawerLayout = findViewById(R.id.drawer_layout);
        expandableList = findViewById(R.id.navigationmenu);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        getNewFromGoogle();
        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        expandableList.setAdapter(mMenuAdapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Log.d("DEBUG", "submenu item clicked");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");
                switch (i) {
                    case 2:
                        startActivity(new Intent(MainActivity.this, VersionActivity.class));
                }
                Toast.makeText(MainActivity.this, "Group clicked--", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("HOME");
        item1.setIconImg(R.drawable.ic_home);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("LOG IN");
        item2.setIconImg(R.drawable.ic_home);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("ABOUT");
        item3.setIconImg(R.drawable.ic_home);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("EXIT");
        item4.setIconImg(R.drawable.ic_home);
        listDataHeader.add(item4);


        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Login Facebook");

        listDataChild.put(listDataHeader.get(1), heading1);// Header, Child data
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.trim().isEmpty() && query.trim().matches("^[0-9]*$")){
                    Intent intent = new Intent(MainActivity.this, NumberText.class);
                    intent.putExtra("number", query);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Input is not valid", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

       /* searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#256321")));
                }
                return true;
            }
        });*/

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getNewFromGoogle() {

        Call<GoogleNewsResponse> call = RetrofitClient.getInstance().getApi().getNews("google-news", "cc39eb8a0bf94781933a765ee91dd8a5");

        call.enqueue(new Callback<GoogleNewsResponse>() {
            @Override
            public void onResponse(Call<GoogleNewsResponse> call, Response<GoogleNewsResponse> response) {
                progressBar.setVisibility(View.GONE);
                progressBarTwo.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    return;
                }

                GoogleNewsResponse googleNewsResponse = response.body();
                if (googleNewsResponse != null && googleNewsResponse.getStatus().equalsIgnoreCase("ok")) {

                    List<Articles> articlesList = googleNewsResponse.getArticles();
                    GoogleNewsAdapter googleNewsAdapter = new GoogleNewsAdapter(articlesList);
                    recyclerView.setAdapter(googleNewsAdapter);

                    GoogleNewsAdapterReverseOrder reverseOrder = new GoogleNewsAdapterReverseOrder(articlesList);
                    recyclerViewTwo.setAdapter(reverseOrder);

                } else {
                    if (googleNewsResponse != null) {
                        Log.d(TAG, "onResponse:  data is properly not received");
                    } else {
                        Log.d(TAG, "onResponse: response is null");
                    }
                }
            }

            @Override
            public void onFailure(Call<GoogleNewsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                progressBarTwo.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: Google News Response" + t.getMessage());
            }
        });

    }

}
