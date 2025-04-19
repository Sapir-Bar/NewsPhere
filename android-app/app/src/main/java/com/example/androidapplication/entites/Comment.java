package com.example.androidapplication.entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Comment {

    @PrimaryKey (autoGenerate = true)
    private int commentId;
    private final int postId;
    private final String author_nickname;
    private final String author_image;
    private String content;

    public Comment(String author_nickname, String author_image, String content, int postId) {
        this.author_nickname = author_nickname;
        this.author_image = author_image;
        this.content = content;
        this.postId = postId;
    }
    public String getAuthor_nickname() {
        return author_nickname;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public String getContent() {
        return content;
    }

    public int getPostId() {
        return postId;
    }

    public int getCommentId() { return commentId; }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentId(int commentId) { this.commentId = commentId; }
}


