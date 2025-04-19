package com.example.androidapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapplication.entites.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM posts")
    List<Post> index();

    @Query("SELECT * FROM posts WHERE _id = :postId")
    LiveData<Post> get(String postId);

    @Insert
    void insert(Post... posts);

    @Insert
    void insert(List<Post> posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);

    @Query("DELETE FROM posts")
    void clear();


}
