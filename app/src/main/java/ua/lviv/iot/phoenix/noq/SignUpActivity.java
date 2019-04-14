package ua.lviv.iot.phoenix.noq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText dataEdit;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText passwordRepeatEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String password = extras.getString("password");

        emailEdit = findViewById(R.id.enter_email);
        passwordEdit = findViewById(R.id.enter_password);
        dataEdit = findViewById(R.id.enter_data);
        nameEdit = findViewById(R.id.enter_name);
        phoneEdit = findViewById(R.id.enter_telephone);
        passwordRepeatEdit = findViewById(R.id.enter_repassword);

        emailEdit.setText(email);
        passwordEdit.setText(password);

        Button button = findViewById(R.id.login);

        button.setOnClickListener((View v) -> {
            if (!validateForm()) {
                return;
            }
            User user = new User(
                    nameEdit.getText().toString(), emailEdit.getText().toString(),
                    dataEdit.getText().toString(), phoneEdit.getText().toString());
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
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

        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
            nameEdit.setError("Required");
            result = false;
        } else {
            nameEdit.setError(null);
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

        if(passwordEdit.getText().toString() != passwordRepeatEdit.getText().toString()) {
            passwordRepeatEdit.setError("Passwords do not match");
            result = false;
        } else {
            passwordRepeatEdit.setError(null);
        }

        return result;
    }
}
