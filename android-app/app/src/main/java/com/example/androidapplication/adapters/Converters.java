package com.example.androidapplication.adapters;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.example.androidapplication.entites.Comment;
import com.example.androidapplication.entites.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Uri uriFromString(String value) {
        return value == null ? null : Uri.parse(value);
    }

    @TypeConverter
    public static String uriToString(Uri uri) {
        return uri == null ? null : uri.toString();
    }


    @TypeConverter
    public static List<Comment> commentsFromString(String value) {
        Type listType = new TypeToken<List<Comment>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String commentsToString(List<Comment> comments) {
        Gson gson = new Gson();
        return gson.toJson(comments);
    }

    @TypeConverter
    public static User userFromString(String value) {
        Type listType = new TypeToken<User>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String userToString(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? new ArrayList<>() : Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String toString(List<String> list) {
        return list == null ? null : String.join(",", list);
    }
}

