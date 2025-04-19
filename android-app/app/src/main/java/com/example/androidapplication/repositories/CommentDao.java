package com.example.androidapplication.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapplication.entites.Comment;

import java.util.List;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comment")
    List<Comment> index();

    @Query("SELECT * FROM comment where postId = :postId" )
    List<Comment> get(int postId);

    @Insert
    void insert(Comment... comments);

    @Update
    void update(Comment... comments);

    @Delete
    void delete(Comment... comments);
}
