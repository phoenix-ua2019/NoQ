package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.fragments.ListOfMealsFragment;
import ua.lviv.iot.phoenix.noq.fragments.MainFragment;
import ua.lviv.iot.phoenix.noq.fragments.OrderFragment;
import ua.lviv.iot.phoenix.noq.models.User;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ValueEventListener {

    private String email;
    private String name;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Fragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, fragment).commit();
/*
        System.out.println(savedInstanceState);
        if (mAuth.getCurrentUser() != null)
            Useful.userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(this);
*/

        try {
             email = mAuth.getCurrentUser().getEmail();
             name = mAuth.getCurrentUser().getDisplayName();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please, log in again", Toast.LENGTH_LONG).show();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUserInfoIntoNavDrawer(name, email);
    }

    public void setUserInfoIntoNavDrawer(String name, String email) {
        View header = navigationView.getHeaderView(0);

        TextView emailField = header.findViewById(R.id.header_email);
        emailField.setText(email);

        TextView nameField = header.findViewById(R.id.header_name);
        nameField.setText(name);

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
        boolean result = false;

        Fragment fragment = null;

        int id = menuItem.getItemId();
        if (id == R.id.menu) {
            fragment = new MainFragment();
            Toast.makeText(getApplicationContext(), "Вы выбрали камеру", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.user) {

        } else if (id == R.id.star) {
            fragment = new ListOfMealsFragment();
        } else if (id == R.id.setting) {
            startActivity(new Intent(this, ListOfMeals.class));

        } else if (id == R.id.exit) {
            if (mAuth == null)
                return false;
            mAuth.signOut();
            overridePendingTransition(R.anim.right_in, R.anim.rotate);
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        } else {
            result = true;
            System.out.println("default");
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.base_for_nv, fragment);

            fragmentTransaction.commit();
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

        closeDrawer();
        return result;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
