package com.wwschrader.android.scavengehunter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Warren on 12/8/2016.
 * For hosting the admin related fragments.
 */

@SuppressWarnings("FieldCanBeLocal")
public class NavigationActivity extends AppCompatActivity {
    @SuppressWarnings("FieldCanBeLocal")
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private View headerLayout;
    private TextView headerName;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        //inflate header on nav view and add user name to it
        headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header);
        headerName = (TextView) headerLayout.findViewById(R.id.header_name);
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser != null){
            headerName.setText(mFirebaseUser.getDisplayName());
        }


        //setup animated hamburger icon
        mActionBarDrawerToggle = setupDrawerToggle();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, new HomeFragment());
        fragmentTransaction.commit();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void selectDrawerItem(MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_home:
            case R.id.navigation_admin:
                swapChildFragment(item.getItemId());
                break;
            case R.id.navigation_logoff:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(NavigationActivity.this, MainActivity.class));
                                finish();
                            }
                        });
        }

        item.setCheckable(true);
        mDrawerLayout.closeDrawers();
    }

    private void swapChildFragment(int itemId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.navigation_home:
                fragmentTransaction.replace(R.id.child_fragment_container, new HomeFragment());
                fragmentTransaction.commit();
                break;
            case R.id.navigation_admin:
                fragmentTransaction.replace(R.id.child_fragment_container, new ViewPagerFragment());
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mActionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //sync toggle state after onRestoreInstance
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //pass configuration change to drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
