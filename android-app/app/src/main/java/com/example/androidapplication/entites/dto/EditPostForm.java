package com.example.androidapplication.entites.dto;

public class EditPostForm {

    private String postContent;
    private String postImage;

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public EditPostForm(String postContent, String postImage) {
        this.postContent = postContent;
        this.postImage = postImage;
    }
}
