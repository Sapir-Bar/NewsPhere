package com.example.androidapplication.entites.dto;

public class CreateUserForm {

    private String username;
    private String password;
    private String nickname;
    private String image;

    public CreateUserForm(String username, String password, String nickname, String image) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
