package com.ntobeko.confmanagement.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    private String email, password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean emailValid(){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean passwordValid(){
        return password.length() > 6;
    }
}
