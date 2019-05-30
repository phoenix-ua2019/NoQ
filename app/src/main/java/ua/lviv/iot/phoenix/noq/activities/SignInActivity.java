package ua.lviv.iot.phoenix.noq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ua.lviv.iot.phoenix.noq.R;
import ua.lviv.iot.phoenix.noq.models.User;

public class SignInActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText mEmailField;
    private EditText mPasswordField;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            //Useful.onAuthSuccess(this, mAuth.getCurrentUser());
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);


        Button mSignInButton = findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(this);

        mEmailField = findViewById(R.id.login_email);
        mPasswordField = findViewById(R.id.login_password);

        mPasswordField.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mSignInButton.performClick();
                return true;
            }
            return false;
        });

        findViewById(R.id.logo_sign_in).setOnClickListener(this);

        mGoogleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Useful.google_api_key).requestEmail().build());
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
                        startActivity(new Intent(this, MainActivity.class));
                        //Useful.onAuthSuccess(this, task.getResult().getUser());
                        finish();
                        return;
                    }
                    Toast.makeText(SignInActivity.this, "Sign In Failed:"
                            +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                });
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this,
                (@NonNull Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        User mUser = new User(acct.getDisplayName(), acct.getEmail());
                        new Useful(this).onAuthSuccess(user, mUser);
                        finish();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(findViewById(R.id.logo_sign_in), "Authentication Failed:"
                                +task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    void googleSignIn() {
        Log.d(TAG, "googleSignUp");
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.logo_sign_in) {
            googleSignIn();
        } else if (i == R.id.sign_in) {
            signIn();
        } else if (i == R.id.sign_up) {
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}