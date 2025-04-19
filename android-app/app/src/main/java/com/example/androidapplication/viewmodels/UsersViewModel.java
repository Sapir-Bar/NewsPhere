package com.example.androidapplication.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreateUserForm;
import com.example.androidapplication.entites.dto.Token;
import com.example.androidapplication.entites.dto.UserLoginForm;
import com.example.androidapplication.repositories.UsersRepository;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository repository;
    private LiveData<List<User>> users;
    private LiveData<CurrentUser> currentUser;


    public UsersViewModel (@NonNull Application application) {
        super(application);
        repository = new UsersRepository();
        users = repository.getAll();
        currentUser = repository.getCurrentUser(getApplication());
    }

    public LiveData<List<User>> getAll() { return users; }

    public LiveData<User> get(String userId) { return repository.get(userId); }

    public LiveData<CurrentUser> getCurrentUser() {
        if(currentUser.getValue() == null) {
            currentUser = repository.getCurrentUser(getApplication());
        }
        return currentUser;
    }

    public void add(CreateUserForm form) { repository.add(form); }
    public void delete(User user) { repository.delete(user); }
    public void update(User user) { repository.update(user); }
    public void reload() { repository.reload(); }
    public LiveData<Token> loginUser(Context context, UserLoginForm form) { return repository.loginUser(context, form);}
    public LiveData<CurrentUser> toggleFriend(String idOther) {
        return repository.toggleFriend(getApplication(), idOther);
    }

}
