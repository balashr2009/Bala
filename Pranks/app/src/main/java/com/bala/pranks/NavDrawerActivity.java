package com.bala.pranks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bala.pranks.home.HomeFragment;
import com.bala.pranks.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        textViewName = findViewById(R.id.tvwelcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        //bottomNavigationView.setVisibility(View.VISIBLE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.getMenu().getItem(0);
        toggle.syncState();
        textViewName.setText("Welcome "+getIntent().getStringExtra("username"));
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.Framelayout, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment SelectedFrag = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            SelectedFrag = new HomeFragment();
                            break;
                        case R.id.navigation_search:
                            SelectedFrag = new SearchFragment();
                            break;
                        case R.id.navigation_notifications:
                            SelectedFrag = new NotificationsFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Framelayout, SelectedFrag).commit();

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        // bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_dashboard:
                Toast.makeText(getApplicationContext(),"Dashboard Menu Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_gallery:
                Intent i=new Intent(getApplicationContext(),GalleryActivity.class);
                    startActivity(i);
                break;
            case R.id.nav_slideshow:
                Toast.makeText(getApplicationContext(),"Slide Menu Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(),"Share Menu Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Rateus:
                Toast.makeText(getApplicationContext(),"Rateus Menu Clicked",Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Toast.makeText(this, "Profile Menu Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings Menu Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_signout:
                Toast.makeText(this, "Signout Menu Clicked", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}