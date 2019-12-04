package com.infideap.atomexample

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import com.infideap.atomic.Atom.Companion.with
import com.infideap.atomic.FutureCallback

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val usernameEditText = findViewById<View>(R.id.editText_username) as EditText
        val passwordEditText = findViewById<View>(R.id.editText_password) as EditText
        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        findViewById<View>(R.id.button_login).setOnClickListener { v ->
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val loginRequest = LoginRequest(username, password)
            with(this@LoginActivity)
                    .load("https://reqres.in/api/login")
                    .setJsonPojoBody(loginRequest)
                    .`as`(LoginResponse::class.java)
                    .setCallback(object : FutureCallback<LoginResponse> {
                        override fun onCompleted(e: Exception?, result: LoginResponse) {
                            if (e != null) {
                                e.printStackTrace()
                            } else if (result.token != null) {
                                Snackbar.make(v, "Pass : " + result.token, Snackbar.LENGTH_LONG).show()
                            } else if (result.error != null) {
                                Snackbar.make(v, "Fail : " + result.error, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    })
        }
    }
}