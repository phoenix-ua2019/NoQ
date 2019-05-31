package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.fragments.*;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Useful useful;
    private Fragment fragment;
    private NavigationView navigationView;
    private String pointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragment = new ListOfCafesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_for_nv, fragment)
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .commit();
        pointer = "ListOfCafesFragment";

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Оберіть кафе");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.make_order);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        useful = new Useful(navigationView, this);
        useful.setUser();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        navigationView.setCheckedItem(menuItem);

        Fragment fragment = null;

        if (id == R.id.make_order) {
            fragment = new ListOfCafesFragment();
            fragment.setArguments(new Bundle());
            toolbar.setTitle("Оберіть кафе");
            pointer = "ListOfCafesFragment";

        } else if (id == R.id.user) {
            fragment = new UserFragment();
            Bundle b = new Bundle();
            b.putParcelable("user_icon", useful.getUser());
            fragment.setArguments(b);
            toolbar.setTitle("Профіль");
            pointer = "UserFragment";
        } else if (id == R.id.my_orders) {
            fragment = new MyOrdersFragment();
            fragment.setArguments(new Bundle());
            toolbar.setTitle("Мої замовлення");
            pointer = "MyOrdersFragment";

        } else if (id == R.id.setting) {
            fragment = new SettingsFragment();
            toolbar.setTitle("Налаштування");
            pointer = "SettingsFragment";

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
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.base_for_nv, fragment)
                    .commit();
            this.fragment = fragment;
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            switch (pointer) {
                case "ListOfCafesFragment": break;
                case "ListOfMealsFragment": {
                    b1Reverse();
                    break;
                }
                case "TimeFragment": {
                    b2Reverse();
                    break;
                }
                case "OrderFragment": {
                    b3Reverse();
                    break;
                }
                case "UserFragment": {
                    b1Reverse();
                    break;
                }
                case "MyOrdersFragment": {
                    b1Reverse();
                    break;
                }
                case "SettingsFragment": {
                    b1Reverse();
                    break;
                }
                case "SeeMyOrderFragment": {
                    b5Reverse();
                    break;
                }
                default: super.onBackPressed();
            }
        }
    }

    public void b1(View view) {
        setFragment(new ListOfMealsFragment());
        pointer = "ListOfMealsFragment";
        toolbar.setTitle("Оберіть страви");
    }

    public void b1Reverse() {
        setFragmentReverse(new ListOfCafesFragment());
        pointer = "ListOfCafesFragment";
        toolbar.setTitle("Оберіть Кафе");
        navigationView.setCheckedItem(R.id.make_order);
    }

    public void b2(View view) {
        setFragment(new TimeFragment());
        pointer = "TimeFragment";
        toolbar.setTitle("Оберіть час");
    }

    public void b2Reverse() {
        setFragmentReverse(new ListOfMealsFragment());
        pointer = "ListOfMealsFragment";
        toolbar.setTitle("Оберіть Страви");
    }

    public void b3(View view) {
        setFragment(new OrderFragment());
        pointer = "OrderFragment";
        toolbar.setTitle("Ваше замовлення");
    }

    public void b3Reverse() {
        setFragmentReverse(new TimeFragment());
        pointer = "TimeFragment";
        toolbar.setTitle("Оберіть час");
    }

    public void b4(View view) {
        Bundle args = fragment.getArguments();
        fragment = new MyOrdersFragment();
        fragment.setArguments(args);
        toolbar.setTitle("Мої замовлення");
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.base_for_nv, fragment)
                .commit();
        pointer = "MyOrdersFragment";
    }

    public void b5(View view) {
        setFragment(new SeeMyOrderFragment());
        toolbar.setTitle("Ваше замовлення");
        navigationView.setCheckedItem(R.id.my_orders);
        pointer = "SeeMyOrderFragment";
    }

    public void b5Reverse() {
        setFragmentReverse(new MyOrdersFragment());
        toolbar.setTitle("Мої замовлення");
        navigationView.setCheckedItem(R.id.my_orders);
        pointer = "MyOrdersFragment";
    }

    public void b6(View view) {
        setFragment(new ListOfCafesFragment());
        pointer = "ListOfCafesFragment";
        toolbar.setTitle("Оберіть кафе");
        navigationView.setCheckedItem(R.id.make_order);

    }


    private void setFragment(Fragment new_fragment) {
        Bundle args = fragment.getArguments();
        fragment = new_fragment;
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.base_for_nv, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setFragmentReverse(Fragment new_fragment) {
        Bundle args = fragment.getArguments();
        fragment = new_fragment;
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.base_for_nv, fragment)
                .addToBackStack(null)
                .commit();
    }
}
