package com.table.tableapp;

import android.os.Bundle;
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
import com.table.tableapp.connection.SeekBarWrapper;

public class menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private PathRequest pr;

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
                R.id.nav_home, R.id.nav_color, R.id.nav_speed
        ).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        pr = new PathRequest(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        ((AppCompatSeekBar)findViewById(R.id.brightnessSlider)).setOnSeekBarChangeListener(
                pr.createSeekBarChangeListener("brightness", "b", new SeekBarWrapper() {
                    @Override
                    public int convert(int value) {
                        return (int)(Math.pow(value, 2) / 255);
                    }

                    @Override
                    public void setText(Integer value) {
                        // Don't set any text! :O
                    }
                })
        );

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