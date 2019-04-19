package ua.lviv.iot.phoenix.noq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText dataEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText passwordRepeatEdit;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        String email="", password="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            password = extras.getString("password");
        }

        emailEdit = findViewById(R.id.enter_email);
        passwordEdit = findViewById(R.id.enter_password);
        dataEdit = findViewById(R.id.enter_data);
        nameEdit = findViewById(R.id.enter_name);
        phoneEdit = findViewById(R.id.enter_telephone);
        passwordRepeatEdit = findViewById(R.id.enter_repassword);

        if (!email.equals("") && !password.equals("")) {
            emailEdit.setText(email);
            passwordEdit.setText(password);
            Task<AuthResult> res = mAuth.createUserWithEmailAndPassword(email, password);
            //System.out.println(res.getResult());
            res.addOnCompleteListener(SignUpActivity.this, (@NonNull Task<AuthResult> task) -> {
                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    System.out.println(res.getResult());
                    user = task.getResult().getUser();
                    return;
                }
                Toast.makeText(SignUpActivity.this, "Sign Up Failed",
                        Toast.LENGTH_SHORT).show();
            });
        }

        Button button = findViewById(R.id.login);

        button.setOnClickListener((View v) -> {
            if (!validateForm()) {
                return;
            }
            User mUser = new User(
                    nameEdit.getText().toString(), emailEdit.getText().toString(),
                    dataEdit.getText().toString(), phoneEdit.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("authentication").child("users")
                    .child(user.getUid()).setValue(mUser);
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(emailEdit.getText().toString())) {
            emailEdit.setError("Required");
            result = false;
        } else {
            emailEdit.setError(null);
        }

        if (TextUtils.isEmpty(passwordEdit.getText().toString())) {
            passwordEdit.setError("Required");
            result = false;
        } else {
            passwordEdit.setError(null);
        }

        if (TextUtils.isEmpty(passwordRepeatEdit.getText().toString())) {
            passwordRepeatEdit.setError("Required");
            result = false;
        } else {
            passwordRepeatEdit.setError(null);
        }

        if(!passwordEdit.getText().toString().equals(passwordRepeatEdit.getText().toString())) {
            passwordRepeatEdit.setError("Passwords do not match");
            result = false;
        } else {
            passwordRepeatEdit.setError(null);
        }

        return result;
    }
}
