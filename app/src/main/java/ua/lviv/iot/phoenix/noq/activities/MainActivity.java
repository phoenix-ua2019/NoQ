package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.fragments.*;
import ua.lviv.iot.phoenix.noq.models.Order;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Useful useful;
    private Fragment fragment;Boolean cafeUpdated = false, userUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragment = new ListOfCafesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.base_for_nv, fragment).commit();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Оберіть кафе");
        setSupportActionBar(toolbar);

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
            fragment.setArguments(new Bundle());
            toolbar.setTitle("Оберіть кафе");
            //this.fragment.getArguments()

        } else if (id == R.id.user) {
            fragment = new UserFragment();
            Bundle b = new Bundle();
            b.putParcelable("user_icon", useful.getUser());
            fragment.setArguments(b);
            toolbar.setTitle("Профіль");
        } else if (id == R.id.my_orders) {

            fragment = new MyOrdersFragment();
            fragment.setArguments(new Bundle());
            toolbar.setTitle("Мої замовлення");

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
            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(this.fragment.getId(), fragment)
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
        setFragment(new ListOfMealsFragment());
        toolbar.setTitle("Оберіть страви");
    }

    public void b2(View view) {
        setFragment(new TimeFragment());
        toolbar.setTitle("Оберіть час");
    }

    public void b3(View view) {
        setFragment(new OrderFragment());
        toolbar.setTitle("Ваше замовлення");
    }

    public void b4(View view) {
        Bundle args = fragment.getArguments();
        System.out.println(args);
        fragment = new MyOrdersFragment();
        fragment.setArguments(args);
        toolbar.setTitle("Ваші замовлення");
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.base_for_nv, fragment)
                .commit();
    }

    public void b5(View view) {
        setFragment(new SeeMyOrderFragment());
        toolbar.setTitle("Ваше замовлення");
    }

    public void b6(View view) {
        setFragment(new ListOfCafesFragment());
        toolbar.setTitle("Ваше замовлення");
    }


    private void setFragment(Fragment new_fragment) {
        Bundle args = fragment.getArguments();
        fragment = new_fragment;
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.base_for_nv, fragment)
                .commit();
    }

}
