package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
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

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;


public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_user);
        NavigationView navigationView = findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        TextView name = findViewById(R.id.user_name);
        TextView email = findViewById(R.id.user_email);
        TextView phone = findViewById(R.id.user_phone);
        TextView date = findViewById(R.id.user_date);
        Button pass = findViewById(R.id.user_pass);

        Bundle extras = getIntent().getExtras();
        User mUser = extras.getParcelable("user");

        name.setText(mUser.getName());
        email.setText(mUser.getEmail());
        phone.setText(mUser.getPhone());
        date.setText(mUser.getDate());

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(this,ChangePasswordActivity.class)));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.user) {

            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (menuItem.getItemId() == R.id.menu) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (menuItem.getItemId() == R.id.star) {

        } else if (menuItem.getItemId() == R.id.setting) {

        } else if (menuItem.getItemId() == R.id.exit) {

        }

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        }
        super.onBackPressed();
    }
}
