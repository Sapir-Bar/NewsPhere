package com.example.androidapplication.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey (autoGenerate = false)
    @NonNull
    @SerializedName("_id")
    private String _id; //Object Id of the post
    private User author; //ObjectId of the author
    private String content;
    private String image;
    private String date;
    private int likes;
    private List<Comment> comments;

    public Post() {}

    public Post(String _id, User User, String content, String image, String date) {
        this._id = _id;
        this.author = User;
        this.content = content;
        this.image = image;
        this.date = date;
    }

    public Post(Post original) {
        this._id = original._id;
        this.author = original.author;
        this.content = original.content;
        this.image = original.image;
        this.date = original.date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String get_id() { return _id; }

    public String getImage() {
        return image;
    }

    public String getDate() { return date;  }

    public int getLikes() {
        return likes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Comment> getComments(){
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(int commentID) {
        comments.remove(commentID);
    }
}
