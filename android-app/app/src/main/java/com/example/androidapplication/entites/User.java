package com.example.androidapplication.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity (tableName = "user")
public class User {

    @PrimaryKey (autoGenerate = false)
    @NonNull
    @SerializedName("_id")
    private String _id;
    private String username;
    private String password;
    private String nickname;
    private String image;
    private List<String> posts; // ObjectID(s)
    private List<String> friends; // ObjectID(s)

    public User(String _id, String username, String password, String nickname, String image, List<String> posts, List<String> friends ) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.image = (image != null) ? image : "https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png";
        this.posts = posts;
        this.friends = friends;
    }

    public User(String username, String password, String nickname, String image){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.image = (image != null) ? image : "https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png";
    }

    public User() {}

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getNickname() { return nickname; }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<String> getPosts() { return posts; }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {  this.friends = friends; }
}
