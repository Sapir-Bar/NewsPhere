package com.example.androidapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM current_user LIMIT 1")
    LiveData<CurrentUser> getCurrentUser(); // when the table changes, the liveData updated

    @Query("DELETE FROM current_user")
    void deleteCurrentUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upserCurrentUser(CurrentUser user);

    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE _id = :userId")
    LiveData<User> get(String userId);

    @Insert
    void insert(User... users);

    @Insert
    void insert(List<User> users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("DELETE FROM user")
    void clear();
}
