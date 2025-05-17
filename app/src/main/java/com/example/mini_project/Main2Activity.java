package com.example.mini_project;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.mini_project.ui.gallery.GalleryFragment;
import com.example.mini_project.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mini_project.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {

    float x1, x2, y1, y2;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain2.toolbar);
        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        new Thread(() -> {
//
//
//        }).start();
        startService(new Intent(this, MQTTClient.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);  // user has to manually allow it
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
//    public boolean onTouchEvent(MotionEvent touchEvent){
//        switch(touchEvent.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1 = touchEvent.getX();
//                y1 = touchEvent.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = touchEvent.getX();
//                y2 = touchEvent.getY();
//                float deltaX = x2 - x1;
//                if (Math.abs(deltaX) > 100) {
//                    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
//                    if(deltaX > 0){
////                        Intent i = new Intent(Main2Activity.this, GalleryFragment.class);
////                        startActivity(i);
////                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                        navController.navigate(R.id.nav_gallery);
//                    }else if(deltaX < 0){
////                        Intent i = new Intent(Main2Activity.this, SlideshowFragment.class);
////                        startActivity(i);
////                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        navController.navigate(R.id.nav_slideshow);
//                    }}
//                break;
//        }
//        return false;
//    }
}