package com.table.tableapp;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.table.tableapp.connection.PathRequest;

public class menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private PathRequest pr;

    // minimum delay between request
    private static final long delay = 20;
    private long time = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow).setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        pr = new PathRequest(this);

    }

    AppCompatSeekBar.OnSeekBarChangeListener seekBarChangeListener = new AppCompatSeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            if (System.currentTimeMillis() >= time + delay) {
                time = System.currentTimeMillis();
                int value = (int) (Math.pow(progress, 2) / 255);
                pr.makeStringRequest("brightness/?b=" + value);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int value = (int)(Math.pow(seekBar.getProgress(), 2) / 255);
            pr.makeStringRequest("brightness/?b=" + value);
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        AppCompatSeekBar seekBar = findViewById(R.id.brightnessSlider);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        SwitchCompat onSwitch = findViewById(R.id.onSwitch);
        onSwitch.setOnClickListener(v -> {
            if (onSwitch.isChecked()) {
                pr.makeStringRequest("on");
            } else {
                pr.makeStringRequest("off");
            }
        });

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}