package com.example.androidapplication.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.api.PostAPI;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.dto.CreatePostForm;

import java.util.LinkedList;
import java.util.List;

public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostsRepository() {
        AppDB db = AppDB.getInstance(MyApplication.context);
        dao = db.postDao();
        postListData = new PostListData();
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            //setValue must be called on the main (UI) thread.
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                //postValue can be called from any thread, not just the main thread.
                postListData.postValue(dao.index());
            }).start();
        }
    }
    // Return LiveData (upcasting) to ViewModel layer
    public LiveData<List<Post>> getAll() { return postListData; }
    public LiveData<Post> get(String postId) { return api.get(postId); }
    public void add(Context context, CreatePostForm form){ api.add(context, form); }
    public LiveData<Boolean> getPostCreationResult() { return api.getPostCreationResult(); }
    public void delete(Context context, Post post){ api.delete(context, post); }
    public void update(Context context, Post post){ api.update(context, post); }
    public LiveData<Boolean> getPostUpdateResult() { return api.getPostUpdateResult(); }
    public void reload(Context context) { api.reload(context); }
    public void reloadFriendPosts(Context context, String friendId) { api.reloadFriendPosts(context, friendId); }
    public LiveData<List<Post>> getFriendPosts() { return api.getFriendPosts(); }
}
