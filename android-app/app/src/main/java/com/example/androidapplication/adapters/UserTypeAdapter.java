package com.example.androidapplication.adapters;

import com.example.androidapplication.entites.User;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserTypeAdapter extends TypeAdapter<User> {

    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("_id").value(value.get_id());
        out.name("username").value(value.getUsername());
        out.name("password").value(value.getPassword());
        out.name("nickname").value(value.getNickname());
        out.name("image").value(value.getImage());
        out.name("posts").beginArray();
        for (String postId : value.getPosts()) {
            out.value(postId);
        }
        out.endArray();
        out.name("friends").beginArray();
        for (String friendId : value.getFriends()) {
            out.value(friendId);
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "_id":
                    user.set_id(in.nextString());
                    break;
                case "username":
                    user.setUsername(in.nextString());
                    break;
                case "password":
                    user.setPassword(in.nextString());
                    break;
                case "nickname":
                    user.setNickname(in.nextString());
                    break;
                case "image":
                    user.setImage(in.nextString());
                    break;
                case "posts":
                    user.setPosts(readStringArray(in));
                    break;
                case "friends":
                    user.setFriends(readStringArray(in));
                    break;
                default:
                    in.skipValue(); // Ignore unknown fields
                    break;
            }
        }
        in.endObject();
        return user;
    }

    private List<String> readStringArray(JsonReader in) throws IOException {
        List<String> list = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            list.add(in.nextString());
        }
        in.endArray();
        return list;
    }
}
