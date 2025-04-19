package com.example.androidapplication;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidapplication.viewmodels.PostsViewModel;
import com.example.androidapplication.viewmodels.UsersViewModel;

public class MyApplication extends Application {
    public static Context context;
    public static PostsViewModel postsViewModel;
    public static UsersViewModel usersViewModel;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        usersViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(UsersViewModel.class);
        postsViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(PostsViewModel.class);
    }

}
