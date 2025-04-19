package com.example.androidapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.dto.CreatePostForm;
import com.example.androidapplication.repositories.PostsRepository;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    private PostsRepository repository;
    private LiveData<List<Post>> posts;

    public PostsViewModel (@NonNull Application application) {
        super(application);
        repository = new PostsRepository();
        posts = repository.getAll();
    }

    public LiveData<List<Post>> getAll() { return posts; }
    public LiveData<Post> get(String postId) { return repository.get(postId); }
    public void add(CreatePostForm form) { repository.add(getApplication(), form); }
    public LiveData<Boolean> getPostCreationResult() { return repository.getPostCreationResult(); }
    public void delete(Post post) { repository.delete(getApplication(), post); }
    public void update(Post post) { repository.update(getApplication(), post); }
    public LiveData<Boolean> getPostUpdateResult() { return repository.getPostUpdateResult(); }
    public void reload() { repository.reload(getApplication()); }
    public void reloadFriendPosts (String friendId) { repository.reloadFriendPosts(getApplication(), friendId); }
    public LiveData<List<Post>> getFriendPosts () { return repository.getFriendPosts(); }
}
