package ua.lviv.iot.phoenix.noq.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.activities.ChangePasswordActivity;
import ua.lviv.iot.phoenix.noq.activities.SignInActivity;
import ua.lviv.iot.phoenix.noq.models.User;

public class UserFragment extends Fragment
        implements OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private FragmentActivity activity = getActivity();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        User mUser = getArguments().getParcelable("user_icon");
        ((TextView) view.findViewById(R.id.user_name)).setText(mUser.getName());
        ((TextView) view.findViewById(R.id.user_email)).setText(mUser.getEmail());
        ((TextView) view.findViewById(R.id.user_phone)).setText(mUser.getPhone());
        ((TextView) view.findViewById(R.id.user_date)).setText(mUser.getDate());

        drawerLayout = view.findViewById(R.id.drawer_layout_user);

        Button pass = view.findViewById(R.id.user_pass);

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(getContext(), ChangePasswordActivity.class)));
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean result = false;

        int id = menuItem.getItemId();
        if(id == R.id.user) {
            closeDrawer();
        } else if(id == R.id.make_order) {
            closeDrawer();
            activity.finish();
            activity.overridePendingTransition(R.anim.right_in, R.anim.rotate);
        } else if(id == R.id.my_orders) {
            System.out.println("star");
        } else if(id == R.id.setting) {
            System.out.println("setting");
        } else if(id == R.id.exit) {
            if (mAuth == null)
                return false;
            mAuth.signOut();
            activity.overridePendingTransition(R.anim.right_in,R.anim.rotate);
            activity.finish();
            startActivity(new Intent(getContext(), SignInActivity.class));
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
}
