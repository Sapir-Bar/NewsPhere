package com.example.androidapplication.adapters;

import com.example.androidapplication.entites.Post;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PostTypeAdapter extends TypeAdapter<Post> {

    private final UserTypeAdapter userTypeAdapter;

    public PostTypeAdapter() {
        this.userTypeAdapter = new UserTypeAdapter();
    }

    @Override
    public void write(JsonWriter out, Post post) throws IOException {
        out.beginObject();
        out.name("_id").value(post.get_id());
        out.name("author");
        userTypeAdapter.write(out, post.getAuthor());
        out.name("content").value(post.getContent());
        out.name("image").value(post.getContent());
        out.name("date").value(post.getDate());
        out.endObject();
    }

    @Override
    public Post read(JsonReader in) throws IOException {
        Post post = new Post();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "_id":
                    post.set_id(in.nextString());
                    break;
                case "author":
                    post.setAuthor(userTypeAdapter.read(in));
                    break;
                case "content":
                    post.setContent(in.nextString());
                    break;
                case "image":
                    post.setImage(in.nextString());
                    break;
                case "date":
                    post.setDate(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return post;
    }
}