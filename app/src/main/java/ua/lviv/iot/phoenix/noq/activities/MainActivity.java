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
    private DatabaseReference userRef;
    {
        if (mAuth.getCurrentUser() != null)
        userRef = Useful.userRef.child(mAuth.getCurrentUser().getUid());
    }
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_main);
        ((NavigationView) findViewById(R.id.drawer))
                .setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        userRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
        System.out.println(mUser);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean result = false;
        String itemName = (String) menuItem.getTitle();
        TextView name = findViewById(R.id.header_name);
        TextView email = findViewById(R.id.header_email);

        name.setText(mUser.getName());
        email.setText(mUser.getEmail());

        closeDrawer();
        //тут зробити дію на кнопку
        switch (menuItem.getItemId()) {
            case R.id.user:
                Intent openUserActivity = new Intent(this, UserActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                openUserActivity.putExtra("user", mUser);
                startActivity(openUserActivity);
                break;
            case R.id.menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
