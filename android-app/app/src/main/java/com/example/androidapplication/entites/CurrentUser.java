package com.example.androidapplication.entites;

import androidx.room.Entity;

import java.util.List;

@Entity(tableName="current_user")
public class CurrentUser extends User {
    public CurrentUser(String _id, String username, String password, String nickname, String image, List<String> posts, List<String> friends) {
        super(_id, username, password, nickname, image, posts, friends);
    }
}
