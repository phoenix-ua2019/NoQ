package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;


public class UserActivity extends AppCompatActivity{

    private User mUser;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView date;
    private Button pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phone);
        date = findViewById(R.id.user_date);
        pass = findViewById(R.id.user_pass);

        Bundle extras = getIntent().getExtras();
        mUser = extras.getParcelable("user");

        name.setText(mUser.getUName());
        email.setText(mUser.getEmail());
        phone.setText(mUser.getPhone());
        date.setText(mUser.getDate());

        pass.setOnClickListener((View v) ->
                startActivity(new Intent(this,ChangePasswordActivity.class)));
    }
}
