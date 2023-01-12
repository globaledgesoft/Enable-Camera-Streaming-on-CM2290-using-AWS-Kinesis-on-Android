package com.kvstream.streamvideo.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kvstream.streamvideo.demo.R;
import com.kvstream.streamvideo.demo.webfrag.WebRtcConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.google.android.material.navigation.NavigationView;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = SecondActivity.class.getSimpleName();

    private Fragment streamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            streamFragment = getSupportFragmentManager().findFragmentByTag(WebRtcConfiguration.class.getName());
        }
        this.startConfigFragment();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            AWSMobileClient.getInstance().signOut();
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder()
                            .logo(R.mipmap.kinesisvideo_logo)
                            .backgroundColor(Color.WHITE)
                            .nextActivity(SecondActivity.class)
                            .build(),
                    new Callback<UserStateDetails>() {
                        @Override
                        public void onResult(UserStateDetails result) {
                            Log.d(TAG, "onResult: User sign-in " + result.getUserState());
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "onError: User sign-in", e);
                        }
                    });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_simple, fragment, WebRtcConfiguration.class.getName()).commit();
    }

    public void startConfigFragment() {
        try {
            if (streamFragment == null) {
                streamFragment = WebRtcConfiguration.newInstance(this);
                this.startFragment(streamFragment);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to go back to configure stream.");
            e.printStackTrace();
        }
    }
}
