package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homeactivity extends AppCompatActivity {
    ActionBar actionBar;
    BottomNavigationView userbottomNavigationView,farmerbottomNavigationView;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExitPressedOnce = false;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        getSupportActionBar().hide();
        userbottomNavigationView = findViewById(R.id.userbottamnavigation);
        farmerbottomNavigationView = findViewById(R.id.farmerbottomNavigationView);
        SharedPreferences settings = Homeactivity.this.getSharedPreferences("preference", MODE_PRIVATE);
        if (settings.getString("selector", "").equals("")) {
            Toast.makeText(this, "launch failed", Toast.LENGTH_SHORT).show();
        }
        else if (settings.getString("selector", "").equals("patient")){
            userbottomNavigationView.setVisibility(View.VISIBLE);
        }
        else if (settings.getString("selector", "").equals("doctor")){
            farmerbottomNavigationView.setVisibility(View.VISIBLE);
        }
        userbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.home:
                        fragment = new Homefragment();
                        fragmenttransation(fragment);
                        break;
                    case R.id.chat:
                        fragment = new chat();
                        fragmenttransation(fragment);
                        break;
                    case R.id.myorder:
                        fragment = new myorder();
                        fragmenttransation(fragment);
                        break;
                    case R.id.myprofile:
                        fragment = new profile();
                        fragmenttransation(fragment);
                        break;
                }

                return true;
            }
        });
        farmerbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.home:
                        fragment = new Homefragment();
                        fragmenttransation(fragment);
                        break;
//                    case R.id.chat:
//                        fragment = new chat();
//                        fragmenttransation(fragment);
//                        break;
                    case R.id.myprofile:
                        fragment = new profile();
                        fragmenttransation(fragment);
                        break;
                        case R.id.order:
                        fragment = new order();
                        fragmenttransation(fragment);
                        break;
                }

                return true;
            }
        });
    }
    private void fragmenttransation(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentholder, fragment);
        fragmentTransaction.commit();
    }
}