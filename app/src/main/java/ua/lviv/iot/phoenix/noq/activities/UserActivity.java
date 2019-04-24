package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;


public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private User mUser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_user);

        NavigationView navigationView = findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.user);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        mUser = getIntent().getExtras().getParcelable("user");

        ((TextView) findViewById(R.id.user_name)).setText(mUser.getName());
        ((TextView) findViewById(R.id.user_email)).setText(mUser.getEmail());
        ((TextView) findViewById(R.id.user_phone)).setText(mUser.getPhone());
        ((TextView) findViewById(R.id.user_date)).setText(mUser.getDate());
        Button pass = findViewById(R.id.user_pass);

        //((TextView) findViewById(R.id.header_name)).setText(mUser.getName());
        //((TextView) findViewById(R.id.header_email)).setText(mUser.getEmail());

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(this,ChangePasswordActivity.class)));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean result = false;

        closeDrawer();
        switch (menuItem.getItemId()) {
            case R.id.user:
                //Intent openUserActivity = new Intent(this, UserActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                //openUserActivity.putExtra("user", mUser);
                //startActivity(openUserActivity);
                //finish();
                break;
            case R.id.menu:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.star:
                System.out.println("star");
                break;
            case R.id.setting:
                System.out.println("setting");
                break;
            case R.id.exit:
                if (mAuth != null) mAuth.signOut();
                overridePendingTransition(R.anim.right_in,R.anim.rotate);
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
            default:
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        }
        super.onBackPressed();
    }
}
