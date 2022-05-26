package com.example.jobstar.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.jobstar.R;
import com.example.jobstar.databinding.ActivityMainBinding;
import com.example.jobstar.ui.main.Fragmants.AboutFragment;
import com.example.jobstar.ui.main.Fragmants.HomeFragment;
import com.example.jobstar.ui.main.Fragmants.MyJobsFragment;
import com.example.jobstar.ui.main.Fragmants.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar_clr));

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new HomeFragment()).commit();
                        break;
                    case R.id.nav_about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new AboutFragment()).addToBackStack("AboutFragment").commit();
                        break;
                    case R.id.nav_my_jobs:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new MyJobsFragment()).addToBackStack("MyJobsFragment").commit();
                        break;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new ProfileFragment()).addToBackStack("ProfileFragment").commit();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share Not Implemented", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_message:
                        Toast.makeText(MainActivity.this, "Messages Not Implemented", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);


    }





    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStackImmediate();
            }
        }
    }



}