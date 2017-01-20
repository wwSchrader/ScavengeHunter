package com.wwschrader.android.scavengehunter;

import android.content.Context;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wwschrader.android.scavengehunter.objects.HuntGame;
import com.wwschrader.android.scavengehunter.objects.HuntUser;

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
    private DatabaseReference mDatabaseReference;
    private HuntGame mHuntGame;
    private MenuItem menuNavAdmin, menuNavHunt;
    private Context mContext;
    //hunt key to be publicly accessible to fragments for firebase references
    public static String playerHuntUid;
    public static String adminHuntUid;
    public static String userUid;
    public HuntUser mHuntUser;
    DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        mContext = this;

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
            userUid = mFirebaseUser.getUid();

            //syncs HuntUser with firebase
            userDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot user: dataSnapshot.getChildren()){
                        if (user.getKey().equals(userUid)){
                            mHuntUser = user.getValue(HuntUser.class);
                            //look for active hunt and modify nav drawer options
                            checkDataBaseForHunt();
                        }
                    }

                    //if user doesn't exist in database, push new entry
                    if (mHuntUser == null){
                        HuntUser huntUser = new HuntUser(mFirebaseUser.getDisplayName(), mFirebaseUser.getEmail());
                        userDatabase.child(userUid).setValue(huntUser);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("NavigationActivity", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }

        //to setup enabling or disabling menu item based on user hosting a hunt or participating in hunt
        menuNavAdmin = mNavigationView.getMenu().findItem(R.id.navigation_admin);
        menuNavHunt = mNavigationView.getMenu().findItem(R.id.navigation_hunt);



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
            case R.id.navigation_hunt:
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
            case R.id.navigation_account:
                startActivity(new Intent(this, AccountActivity.class));
        }

        item.setCheckable(true);
        mDrawerLayout.closeDrawers();
    }

    private void swapChildFragment(int itemId) {
        FragmentTransaction fragmentSupportTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        switch (itemId) {
            case R.id.navigation_home:
                fragmentSupportTransaction.replace(R.id.child_fragment_container, new HomeFragment());
                fragmentSupportTransaction.commit();
                break;
            case R.id.navigation_admin:
                bundle.putInt("category", R.id.navigation_admin);
                viewPagerFragment.setArguments(bundle);
                fragmentSupportTransaction.replace(R.id.child_fragment_container, viewPagerFragment);
                fragmentSupportTransaction.commit();
                break;
            case R.id.navigation_hunt:
                bundle.putInt("category", R.id.navigation_hunt);
                viewPagerFragment.setArguments(bundle);
                fragmentSupportTransaction.replace(R.id.child_fragment_container, viewPagerFragment);
                fragmentSupportTransaction.commit();
                break;
            case R.id.navigation_account:
                android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, new AccountFragment());
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

    private void checkDataBaseForHunt() {

        if (mHuntUser.getParticipatingHunt() != null){
            menuNavHunt.setVisible(true);
            menuNavHunt.setEnabled(true);
            playerHuntUid = mHuntUser.getParticipatingHunt();
        } else {
            menuNavHunt.setEnabled(false);
            menuNavHunt.setVisible(false);
            playerHuntUid = null;
        }

        if (mHuntUser.getAdminHunt() != null){
            menuNavAdmin.setVisible(true);
            menuNavAdmin.setEnabled(true);
            adminHuntUid = mHuntUser.getAdminHunt();
        } else {
            menuNavAdmin.setEnabled(false);
            menuNavAdmin.setVisible(false);
            adminHuntUid = null;
        }
    }
}
