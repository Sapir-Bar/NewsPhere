package com.example.androidapplication.entites.dto;

public class CreatePostForm {

    private String content;
    private String image;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CreatePostForm(String content, String image, String date) {
        this.content = content;
        this.image = image;
        this.date = date;
    }
}
