package com.infideap.atomexample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.infideap.atomic.Atom;
import com.infideap.atomic.FutureCallback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = (EditText) findViewById(R.id.editText_username);
        final EditText passwordEditText = (EditText) findViewById(R.id.editText_password);

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                LoginRequest loginRequest = new LoginRequest(username, password);

                Atom.with(LoginActivity.this)
                        .load("https://reqres.in/api/login")
                        .setJsonPojoBody(loginRequest)
                        .as(LoginResponse.class)
                        .setCallback(new FutureCallback<LoginResponse>() {
                            @Override
                            public void onCompleted(Exception e, LoginResponse result) {
                                if (e != null) {
                                    e.printStackTrace();
                                } else if (result.token != null) {
                                    Snackbar.make(v, "Pass : " + result.token, Snackbar.LENGTH_LONG).show();
                                } else if (result.error != null) {
                                    Snackbar.make(v, "Fail : " +result.error, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }

}
