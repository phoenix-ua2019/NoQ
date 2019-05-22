package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;


public class UserActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ValueEventListener {

    private DrawerLayout drawerLayout;
    private User mUser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);

        if (mAuth.getCurrentUser() != null)
            Useful.userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);

        mUser = getIntent().getExtras().getParcelable("user_icon");
        ((TextView) findViewById(R.id.user_name)).setText(mUser.getName());
        ((TextView) findViewById(R.id.user_email)).setText(mUser.getEmail());
        ((TextView) findViewById(R.id.user_phone)).setText(mUser.getPhone());
        ((TextView) findViewById(R.id.user_date)).setText(mUser.getDate());

        Button pass = findViewById(R.id.user_pass);

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(this,ChangePasswordActivity.class)));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean result = false;

        int id = menuItem.getItemId();
        if(id == R.id.user) {
            closeDrawer();
        } else if(id == R.id.make_order) {
            closeDrawer();
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.rotate);
        } else if(id == R.id.my_orders) {
            System.out.println("star");
        } else if(id == R.id.setting) {
            System.out.println("setting");
        } else if(id == R.id.exit) {
            if (mAuth == null)
                return false;
            mAuth.signOut();
            overridePendingTransition(R.anim.right_in,R.anim.rotate);
            finish();
            startActivity(new Intent(this, SignInActivity.class));
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
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
        //((TextView) findViewById(R.id.header_name)).setText(mUser.getName());
 	    //((TextView) findViewById(R.id.header_email)).setText(mUser.getEmail());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        }
        super.onBackPressed();
    }
}
