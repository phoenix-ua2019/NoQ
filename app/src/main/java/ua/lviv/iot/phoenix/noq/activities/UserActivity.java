package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;


import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;


public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private User mUser;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView date;
    private Button pass;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phone);
        date = findViewById(R.id.user_date);
        pass = findViewById(R.id.user_pass);

        Bundle extras = getIntent().getExtras();
        mUser = extras.getParcelable("user");

        name.setText(mUser.getUName());
        email.setText(mUser.getEmail());
        phone.setText(mUser.getPhone());
        date.setText(mUser.getDate());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(this,ChangePasswordActivity.class)));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
