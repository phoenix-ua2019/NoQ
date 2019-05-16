package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.fragments.ListOfCafesFragment;
import ua.lviv.iot.phoenix.noq.fragments.ListOfMealsFragment;
import ua.lviv.iot.phoenix.noq.fragments.MyOrdersFragment;
import ua.lviv.iot.phoenix.noq.fragments.OrderFragment;
import ua.lviv.iot.phoenix.noq.fragments.TimeFragment;
import ua.lviv.iot.phoenix.noq.fragments.UserFragment;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Useful useful;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragment = new ListOfCafesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, fragment).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("fff");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //BottomNavigationView navView = findViewById(R.id.nav_panel);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        useful = new Useful(navigationView, this);
        useful.setUser();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        Fragment fragment = null;

        if (id == R.id.make_order) {
            fragment = new ListOfCafesFragment();
            fragment.setArguments(this.fragment.getArguments());

        } else if (id == R.id.user) {
            fragment = new UserFragment();
            Bundle b = new Bundle();
            b.putParcelable("user_icon", useful.getUser());
            fragment.setArguments(b);
        } else if (id == R.id.my_orders) {

            fragment = new MyOrdersFragment();

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
        drawerLayout = findViewById(R.id.drawer_layout);
        try {


            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void b1(View view) {
        Bundle args = fragment.getArguments();
        fragment = new ListOfMealsFragment();
        fragment.setArguments(args);
        setFragment();
    }

    public void b2(View view) {
        Bundle args = fragment.getArguments();
        fragment = new TimeFragment();
        fragment.setArguments(args);
        setFragment();
    }

    public void b3(View view) {
        Bundle args = fragment.getArguments();
        fragment = new OrderFragment();
        fragment.setArguments(args);
        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.base_for_nv, fragment)
                .commit();
    }
}
