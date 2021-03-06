package com.example.hackedin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Login extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button buttonloginSubmit;
    App app;
    private static final String appID = "application-0-aybxr";
    private static final String LOG_TAG =Login.class.getSimpleName();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonloginSubmit = (Button) findViewById(R.id.login_button);
        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        app = new App(new AppConfiguration.Builder(appID).build());

        buttonloginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                userDetail.setEmailId(email);
                userDetail.setPassword(password);
                Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Successfully authenticated using an email and password.");
                        user=app.currentUser();

                        userDetail.setUser(user);

                        Intent intent = new Intent(Login.this, home.class);
                        startActivity(intent);

                    } else {
                        String error="Error: "+it.getError().toString();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                        Log.e(LOG_TAG, it.getError().toString());
                    }
                });
            }
        });
    }

}