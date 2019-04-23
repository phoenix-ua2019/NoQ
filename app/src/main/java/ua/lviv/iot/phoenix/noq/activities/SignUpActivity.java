package ua.lviv.iot.phoenix.noq.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText dateEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText passwordRepeatEdit;

    FirebaseUser user;

    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            Useful.onAuthSuccess(this, mAuth.getCurrentUser());
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEdit = findViewById(R.id.enter_email);
        dateEdit = findViewById(R.id.enter_data);
        nameEdit = findViewById(R.id.enter_name);
        phoneEdit = findViewById(R.id.enter_telephone);
        passwordEdit = findViewById(R.id.enter_password);
        passwordRepeatEdit = findViewById(R.id.enter_repassword);

        findViewById(R.id.sign_up).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
        findViewById(R.id.logo_sign_up).setOnClickListener(this);
    }
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }
        Task<AuthResult> res = mAuth.createUserWithEmailAndPassword(
                emailEdit.getText().toString(), passwordEdit.getText().toString());
        res.addOnCompleteListener(SignUpActivity.this, (@NonNull Task<AuthResult> task) -> {
            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
            System.out.println(task);
            System.out.println(task.getResult());
            if (!task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            user = task.getResult().getUser();
            user = (user != null) ? user : FirebaseAuth.getInstance().getCurrentUser();
            User mUser = new User(
                    nameEdit.getText().toString(), emailEdit.getText().toString(),
                    dateEdit.getText().toString(), phoneEdit.getText().toString());
            Useful.onAuthSuccess(this, user, mUser);
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        });
    }

    private void googleSignUp() {
        Log.d(TAG, "googleSignUp");
    }

    private boolean validateForm() {
        boolean result = false;
        emailEdit.setError(null);
        phoneEdit.setError(null);
        passwordEdit.setError(null);
        passwordRepeatEdit.setError(null);
        final String email = emailEdit.getText().toString(),
                phone = phoneEdit.getText().toString(),
                pass = passwordEdit.getText().toString(),
                rePass = passwordRepeatEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Required");
        } else if (TextUtils.isEmpty(pass) ) {
            passwordEdit.setError("Required");
        } else if (pass.length() <= 6) {
            passwordEdit.setError("Must be at least 7 chars long");
        } else if (TextUtils.isEmpty(rePass)) {
            passwordRepeatEdit.setError("Required");
        } else if(!passwordEdit.getText().toString().equals(rePass)) {
            passwordRepeatEdit.setError("Passwords do not match");
        } else if (TextUtils.isEmpty(phone)) {
            phoneEdit.setError("Required");
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            phoneEdit.setError("Must be a phone number");
        } else {
            result = true;
        }

        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        System.out.println(i);
        System.out.println(R.id.sign_in);
        System.out.println(R.id.logo_sign_up);
        if (i == R.id.sign_up) {
            signUp();
        } else if (i == R.id.sign_in) {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        } else if (i == R.id.logo_sign_up) {
            googleSignUp();
        }
    }
}
