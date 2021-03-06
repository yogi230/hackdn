package com.example.hackedin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {

    EditText signupEmail;
    EditText signupPassword;
    Button registerButton;
    TextView loginActivity;
    Button buttonloginSubmit;
    App app;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG =MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        signupEmail = findViewById(R.id.input_signup_email);
        signupPassword = findViewById(R.id.input_signup_password);
        registerButton = findViewById(R.id.register_button);
        loginActivity = findViewById(R.id.login_activity);


        Realm.init(this);
        app = new App(new AppConfiguration.Builder(appID).build());

        //register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //login button
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void goToLogin(){
        Intent loginIntent = new Intent(MainActivity.this, Login.class);
        startActivity(loginIntent);
    }

    private void signUp(){
        String email = signupEmail.getText().toString();
        String pwd = signupPassword.getText().toString();
        app.getEmailPassword().registerUserAsync(email, pwd, it ->
        {
            if (it.isSuccess()) {
                Log.v(LOG_TAG, "signup successful");

                Log.v(LOG_TAG, email+" "+ pwd);

                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
            } else {
                String error="Error: "+it.getError().toString();
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                Log.v(LOG_TAG, "signup unsucessful"+it.getError());
            }
        });
    }
}