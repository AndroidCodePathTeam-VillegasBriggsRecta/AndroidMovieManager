package edu.csumb.vill4031.androidmoviemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "btnLogin was clicked");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"btnSignUp was clicked");
                String newUsername = etUsername.getText().toString();
                String newPassword = etPassword.getText().toString();
                signUpUser(newUsername,newPassword);
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempt to login user " + username);
        ParseUser.logInInBackground(username,password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                goMainActivity();
            }
        });
    }

    private void signUpUser(final String newUsername, final String newPassword) {
        Log.i(TAG, "Attempting to signup a new user " + etUsername);
        ParseUser user = new ParseUser();
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i(TAG, "Signup succeeded, let's login");
                    loginUser(newUsername,newPassword);
                    Toast.makeText(LoginActivity.this, "Signed in as " + newUsername, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Something went wrong with signup:",e);
                }
            }
        });
    }


}