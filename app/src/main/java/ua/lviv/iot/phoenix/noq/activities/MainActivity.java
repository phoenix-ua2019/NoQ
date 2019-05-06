package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.fragments.ListOfCafesFragment;
import ua.lviv.iot.phoenix.noq.fragments.ListOfMealsFragment;
import ua.lviv.iot.phoenix.noq.fragments.MainFragment;
import ua.lviv.iot.phoenix.noq.fragments.OrderFragment;
import ua.lviv.iot.phoenix.noq.fragments.TimeFragment;
import ua.lviv.iot.phoenix.noq.fragments.UserFragment;
import ua.lviv.iot.phoenix.noq.models.User;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ValueEventListener {


    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User mUser;
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.nav_cafe:
                    fragment = new ListOfCafesFragment();
                    break;
                case R.id.nav_menu:
                    fragment = new ListOfMealsFragment();
                    break;
                case R.id.nav_time:
                    fragment = new TimeFragment();
                    break;
                case R.id.nav_check:
                    fragment = new OrderFragment();
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.base_for_bnv, fragment);

                fragmentTransaction.commit();
            }
            return false;
        }
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Fragment fragment1 = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, fragment1).commit();

        //Fragment fragment2 = new ListOfCafesFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.base_for_bnv, fragment2).commit();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //BottomNavigationView navView = findViewById(R.id.nav_panel);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
/*
        navView.setOnNavigationItemSelectedListener(MenuItem ->
        {
            Fragment fragment = null;

            switch (MenuItem.getItemId()) {
                case R.id.nav_cafe:
                    fragment = new ListOfCafesFragment();
                    break;
                case R.id.nav_menu:
                    fragment = new ListOfMealsFragment();
                    break;
                case R.id.nav_time:
                    fragment = new TimeFragment();
                    break;
                case R.id.nav_check:
                    fragment = new OrderFragment();
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.base_for_bnv, fragment);

                fragmentTransaction.commit();
            }
            return false;

        });
        */
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
        System.out.println(findViewById(R.id.header_name));
        //((TextView) findViewById(R.id.header_name)).setText(mUser.getName());
        //((TextView) findViewById(R.id.header_email)).setText(mUser.getEmail());
        System.out.println(mUser);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        Fragment fragment = null;

        if (id == R.id.menu) {

            fragment = new MainFragment();

        } else if (id == R.id.user) {

            fragment = new UserFragment();

        } else if (id == R.id.star) {

            System.out.println("star");

        } else if (id == R.id.setting) {

            System.out.println("setting");

        } else if (id == R.id.exit) {

            if (mAuth == null)
                return false;
            mAuth.signOut();
            overridePendingTransition(R.anim.right_in, R.anim.rotate);
            finish();
            startActivity(new Intent(this, SignInActivity.class));

        } else {

            System.out.println("default");
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.base_for_nv, fragment);

            fragmentTransaction.commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout_main);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void b1(View view)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, new ListOfCafesFragment()).commit();
    }

    public void b2(View view)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, new TimeFragment()).commit();
    }

    public void b3(View view)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, new OrderFragment()).commit();
    }
}
