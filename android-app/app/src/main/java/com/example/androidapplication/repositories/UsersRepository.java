package com.example.androidapplication.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.api.UserAPI;
import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreateUserForm;
import com.example.androidapplication.entites.dto.Token;
import com.example.androidapplication.entites.dto.UserLoginForm;

import java.util.LinkedList;
import java.util.List;

public class UsersRepository {
    private UserDao dao;
    private UserListData userListData;
    private UserAPI api;

    public UsersRepository() {
        AppDB db = AppDB.getInstance(MyApplication.context);
        dao = db.userDao();
        userListData = new UserListData();
        api = new UserAPI(userListData, dao);
    }

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
            setValue(new LinkedList<User>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                userListData.postValue(dao.index());
            }).start();
        }
    }

    public LiveData<List<User>> getAll() {
        return userListData;
    }

    public LiveData<User> get(String userId) {
        return api.get(userId);
    }

    public LiveData<CurrentUser> getCurrentUser(Context context) {
        return api.getCurrentUser(context);
    }

    public void add(CreateUserForm form) {
        api.add(form);
    }

    public void delete(User user) {
        api.delete(user);
    }

    public void update(User user) {
        api.update(user);
    }

    public void reload() { api.reload(); }

    public LiveData<Token> loginUser(Context context, UserLoginForm form) {
        return api.loginUser(context, form);
    }

    public LiveData<CurrentUser> toggleFriend(Context context, String idOther) {
        return api.toggleFriend(context, idOther);
    }
}
