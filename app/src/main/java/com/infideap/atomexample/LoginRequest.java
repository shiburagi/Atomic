package com.infideap.atomexample;

/**
 * Created by Shiburagi on 27/08/2017.
 */

class LoginRequest {
    private final String email;
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password=password;
    }
}
