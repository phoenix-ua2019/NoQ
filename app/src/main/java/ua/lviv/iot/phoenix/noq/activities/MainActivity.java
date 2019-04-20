package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;

import ua.lviv.iot.phoenix.noq.R;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ((NavigationView) findViewById(R.id.drawer))
                .setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String itemName = (String) menuItem.getTitle();

        closeDrawer();

        //тут зробити дію на кнопку
        switch (menuItem.getItemId()) {
            case R.id.user:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.rotate);
            case R.id.star:
                openDrawer();
                System.out.println("star");
                return false;
            case R.id.setting:
                closeDrawer();
                System.out.println("setting");
                return false;
            case R.id.exit:
                System.out.println("exit");
                return false;
            default:
                System.out.println("default");
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
