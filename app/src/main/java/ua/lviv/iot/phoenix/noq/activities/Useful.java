package ua.lviv.iot.phoenix.noq.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;

public class Useful implements ValueEventListener {
    User mUser;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseStorage mStorage = FirebaseStorage.getInstance("gs://phoenix-no-q.appspot.com"); {
        System.out.println(mStorage.getReferenceFromUrl("gs://phoenix-no-q.appspot.com"));
    }
    NavigationView nv;
    Activity that;

    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference authRef = ref.child("authentication");
    public static final DatabaseReference
            cafeRef = ref.child("cafes"),
            orderRef = ref.child("orders"),
            userRef = authRef.child("users");

    public static final StorageReference storageRef = mStorage.getReference();
    public static final StorageReference iconsRef = storageRef.child("icons");

    public static final String google_api_key =
            //"648378489334-138kgq7v5fiftdget7pp0vifahki0i2m.apps.googleusercontent.com";
            "648378489334-0kfava9v3evp4123h6bosi4ohojb3pkg.apps.googleusercontent.com";

    public Useful(NavigationView nv, AppCompatActivity that) {
        this.nv = nv;
        this.that = that;
    }

    public Useful(Activity that) {
        this.that = that;
    }

    void onAuthSuccess(FirebaseUser user, User mUser) {
        // Write new user_icon
        userRef.child(user.getUid()).setValue(mUser);
        // Go to MainActivity
        that.startActivity(new Intent(that, MainActivity.class));
    }

    public void setUser() {
        System.out.println(mAuth.getCurrentUser().getDisplayName());
        if (mAuth.getCurrentUser() != null) {
            userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(this);
        }
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
        setUserInfoIntoNavDrawer();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    public void setUserInfoIntoNavDrawer() {
        View header = nv.getHeaderView(0);

        TextView emailField = header.findViewById(R.id.header_email);
        emailField.setText(mUser.getEmail());

        TextView nameField = header.findViewById(R.id.header_name);
        nameField.setText(mUser.getName());
    }
}
