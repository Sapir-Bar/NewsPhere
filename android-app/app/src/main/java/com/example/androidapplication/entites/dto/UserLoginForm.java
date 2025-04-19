package com.example.androidapplication.entites.dto;

public class UserLoginForm {

    private String username;
    private String password;

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginForm(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
