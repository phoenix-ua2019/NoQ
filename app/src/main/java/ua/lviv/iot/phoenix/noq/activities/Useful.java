package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ua.lviv.iot.phoenix.noq.activities.MainActivity;
import ua.lviv.iot.phoenix.noq.models.User;

public class Useful {
    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference cafeRef = ref.child("cafes");
    public static final DatabaseReference userRef = ref.child("authentication").child("users");

    static void onAuthSuccess(AppCompatActivity that, FirebaseUser user) {
        // Write new user
        try {
            userRef.child(user.getUid()).setValue(new User(user.getEmail()));
        } finally {
            // Go to MainActivity
            that.startActivity(new Intent(that, MainActivity.class));
        }
    }

    static void onAuthSuccess(AppCompatActivity that, FirebaseUser user, User mUser) {
        // Write new user
        userRef.child(user.getUid()).setValue(mUser);
        // Go to MainActivity
        that.startActivity(new Intent(that, MainActivity.class));
    }
}
