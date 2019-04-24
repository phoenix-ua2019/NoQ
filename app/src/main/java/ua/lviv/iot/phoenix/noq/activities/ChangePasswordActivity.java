package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;

public class ChangePasswordActivity extends AppCompatActivity implements OnClickListener, ValueEventListener {

    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference userRef = Useful.userRef.child(mAuth.getCurrentUser().getUid());
    private User mUser;

    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mReNewPassword;
    private String old_pass;
    private String new_pass;
    private String re_new_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.change_password);

        userRef.addValueEventListener(this);

        mOldPassword = findViewById(R.id.repass_old_password);
        mNewPassword = findViewById(R.id.repass_new_password);
        mReNewPassword = findViewById(R.id.repass_re_new_password);

        findViewById(R.id.repass_change).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener((View v) -> finish());
    }

    private void changePass() {
        Log.d(TAG, "signIn");
        old_pass = mOldPassword.getText().toString();
        new_pass = mNewPassword.getText().toString();
        re_new_pass = mReNewPassword.getText().toString();
        if (!validateForm()) {
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(mUser.getEmail(),old_pass);
        user.reauthenticate(credential).addOnCompleteListener((@NonNull Task<Void> task) -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "Error auth failed");
                return;
            }
            user.updatePassword(new_pass).addOnCompleteListener((@NonNull Task<Void> _task) -> {
                if (_task.isSuccessful()) {
                    Log.d(TAG, "Password updated");
                } else {
                    Log.d(TAG, "Error password not updated");
                    Toast.makeText(this,
                            "Error: password not updated", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private boolean validateForm() {
        boolean result = false;
        mOldPassword.setError(null);
        mNewPassword.setError(null);
        mReNewPassword.setError(null);

        if (TextUtils.isEmpty(old_pass)) {
            mOldPassword.setError("Required");
        } else if (TextUtils.isEmpty(new_pass)) {
            mNewPassword.setError("Required");
        } else if (TextUtils.isEmpty(re_new_pass)) {
            mReNewPassword.setError("Required");
        } else if (!re_new_pass.equals(new_pass)) {
            mNewPassword.setError("Passwords do not match");
        } else if (new_pass.equals(old_pass)){
            mNewPassword.setError("New password cannot be same as old password");
        } else if (new_pass.length() <= 6) {
            mNewPassword.setError("Must be at least 7 chars long");
        } else {
            result = true;
        }

        return result;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    public void onClick(View v) {
        changePass();
    }
}
