package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ValueEventListener {


    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAuth.getCurrentUser() != null)
        Useful.userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        userRef.addValueEventListener(this);
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
        System.out.println(findViewById(R.id.header_name));
        ((TextView) findViewById(R.id.header_name)).setText(mUser.getName());
 	    ((TextView) findViewById(R.id.header_email)).setText(mUser.getEmail());
        System.out.println(mUser);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean result = false;
        navigationView.setCheckedItem(menuItem);

        int id = menuItem.getItemId();
        if(id == R.id.menu) {
            closeDrawer();
        } else if(id == R.id.user) {
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("user_icon", mUser);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.rotate);
        } else if(id == R.id.star) {
            System.out.println("star");
        } else if(id == R.id.setting) {
            System.out.println("setting");
        } else if(id == R.id.exit) {
            if (mAuth == null)
                return false;
            mAuth.signOut();
            overridePendingTransition(R.anim.right_in,R.anim.rotate);
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        } else {
            result = true;
            System.out.println("default");
        }
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
        drawerLayout = findViewById(R.id.drawer_layout_main);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
