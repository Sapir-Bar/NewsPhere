package com.example.androidapplication.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidapplication.adapters.Converters;
import com.example.androidapplication.entites.Comment;
import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.User;

@Database(entities = {Post.class, Comment.class, User.class, CurrentUser.class}, version = 8)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
    public abstract UserDao userDao();

    // 'synchronized' to ensures that only one thread can enter the method at a time. This is important
    // for a singleton pattern to avoid multiple instances of the singleton being created
    // concurrently, which could lead to unexpected behavior.
    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                       AppDB.class, "database").build();
        }
        return instance;
    }



}
