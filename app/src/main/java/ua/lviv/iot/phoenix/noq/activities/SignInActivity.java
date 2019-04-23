package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq.R;

public class SignInActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.login_email);
        mPasswordField = findViewById(R.id.login_password);
        
        findViewById(R.id.sign_in).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener((View v) ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (@NonNull Task<AuthResult> task) -> {
                    Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Useful.onAuthSuccess(this, task.getResult().getUser());
                        finish();
                        return;
                    }
                    Toast.makeText(SignInActivity.this, "Sign In Failed",
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void googleSignIn() {
        Log.d(TAG, "googleSignIn");
    }

    private boolean validateForm() {
        boolean result = false;
        mEmailField.setError(null);
        mPasswordField.setError(null);
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
        } else if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
        } else {
            result = true;
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logo_sign_in) {
            googleSignIn();
        } else if (i == R.id.sign_in) {
            signIn();
        }
    }
}