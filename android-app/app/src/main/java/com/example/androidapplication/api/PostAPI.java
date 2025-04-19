package com.example.androidapplication.api;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.R;
import com.example.androidapplication.adapters.PostTypeAdapter;
import com.example.androidapplication.adapters.UriTypeAdapter;
import com.example.androidapplication.adapters.UserTypeAdapter;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreatePostForm;
import com.example.androidapplication.entites.dto.EditPostForm;
import com.example.androidapplication.repositories.PostDao;
import com.example.androidapplication.repositories.SPRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private MutableLiveData<List<Post>> friendPosts;
    private MutableLiveData<Boolean> postCreationResult;
    private MutableLiveData<Boolean> postUpdateResult;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        postCreationResult =  new MutableLiveData<>();
        postUpdateResult = new MutableLiveData<>();
        friendPosts = new MutableLiveData<>();
        this.postListData = postListData;
        this.dao = dao;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(Post.class, new PostTypeAdapter())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public MutableLiveData<Boolean> getPostCreationResult() {
        return postCreationResult;
    }

    public MutableLiveData<Boolean> getPostUpdateResult() {
        return postUpdateResult;
    }

    public MutableLiveData<List<Post>> getFriendPosts() { return friendPosts; }

    public void reload(Context context) {
        SPRepo sp = new SPRepo(context);
        boolean hasToken = sp.getToken() != null;
        String token = hasToken ? sp.getToken().toString() : null;
        if(token == null) return;
        String s = "Bearer " + token;
        Call<ServerResponse<List<Post>>> call = webServiceAPI.getPosts(s);
        call.enqueue(new Callback<ServerResponse<List<Post>>>() {
            @Override
            public void onResponse(Call<ServerResponse<List<Post>>> call, Response<ServerResponse<List<Post>>> response) {
                List<Post> posts = response.body().getData();
                new Thread(() -> {
                    dao.clear();
                    dao.insert(response.body().getData());
                    postListData.postValue(dao.index());
                }).start();
            }
            @Override
            public void onFailure(Call<ServerResponse<List<Post>>> call, Throwable t) {
            }
        });
    }

    public void delete(Context context, Post toDelete) {
        SPRepo sp = new SPRepo(context);
        boolean hasToken = sp.getToken() != null;
        String token = hasToken ? sp.getToken().toString() : null;
        if(token == null) return;
        String s = "Bearer " + token;
        Call<ServerResponse<User>> call = webServiceAPI.deletePost(s, toDelete.get_id());
        call.enqueue(new Callback<ServerResponse<User>>() {
            @Override
            public void onResponse(Call<ServerResponse<User>> call, Response<ServerResponse<User>> response) {
                User user = response.body().getData();
                new Thread(() -> {
                    dao.delete(toDelete);
                    postListData.postValue(dao.index());
                }).start();
            }
            @Override
            public void onFailure(Call<ServerResponse<User>> call, Throwable t) {
            }
        });
    }

    public void update(Context context, Post toUpdate) {
        SPRepo sp = new SPRepo(context);
        boolean hasToken = sp.getToken() != null;
        String token = hasToken ? sp.getToken().toString() : null;
        if(token == null) return;
        String s = "Bearer " + token;
        Call<ServerResponse<Post>> call = webServiceAPI.updatePost(s, toUpdate.get_id(),
                                          new EditPostForm(toUpdate.getContent(), toUpdate.getImage()));
        call.enqueue(new Callback<ServerResponse<Post>>() {
            @Override
            public void onResponse(Call<ServerResponse<Post>> call, Response<ServerResponse<Post>> response) {
                if (response.body() != null) {
                    ServerResponse<Post> serverResponse = response.body();
                    if (serverResponse.getMessage().equals("Post updated successfully")) {
                        Post post = response.body().getData();
                        new Thread(() -> {
                            dao.update(post);
                            postListData.postValue(dao.index());
                        }).start();
                        postUpdateResult.postValue(true);
                    } else {
                        postUpdateResult.postValue(false);
                    }
                } else {
                    postUpdateResult.postValue(false);
                }
            }
            @Override
            public void onFailure(Call<ServerResponse<Post>> call, Throwable t) {
                postUpdateResult.postValue(false);
            }
        });
    }

    public void add(Context context, CreatePostForm form){
        SPRepo sp = new SPRepo(context);
        boolean hasToken = sp.getToken() != null;
        String token = hasToken ? sp.getToken().toString() : null;
        if(token == null) return;

        String s = "Bearer " + token;
        Call<ServerResponse<Post>> call = webServiceAPI.createPost(s, "0", form);
        call.enqueue(new Callback<ServerResponse<Post>>() {
            @Override
            public void onResponse(Call<ServerResponse<Post>> call, Response<ServerResponse<Post>> response) {
                if (response.body() != null) {
                    ServerResponse<Post> serverResponse = response.body();
                    if (serverResponse.getMessage().equals("Post created successfully")) {
                        Post post = response.body().getData();
                        new Thread(() -> {
                            dao.insert(post);
                            postListData.postValue(dao.index());
                        }).start();
                        postCreationResult.postValue(true);
                    } else {
                        postCreationResult.postValue(false);
                    }
                } else {
                    postCreationResult.postValue(false);
                }
            }
            @Override
            public void onFailure(Call<ServerResponse<Post>> call, Throwable t) {
                postCreationResult.postValue(false);
            }
        });
    }

    public LiveData<Post> get(String postId) {
        return dao.get(postId);
    }

    public void reloadFriendPosts(Context context, String friendId){
        SPRepo sp = new SPRepo(context);
        boolean hasToken = sp.getToken() != null;
        String token = hasToken ? sp.getToken().toString() : null;
        if(token == null) return;
        String s = "Bearer " + token;
        Call<ServerResponse<List<Post>>> call = webServiceAPI.getFriendPosts(s, friendId);
        call.enqueue(new Callback<ServerResponse<List<Post>>>() {
            @Override
            public void onResponse(Call<ServerResponse<List<Post>>> call, Response<ServerResponse<List<Post>>> response) {
                friendPosts.postValue(response.body().getData());
            }
            @Override
            public void onFailure(Call<ServerResponse<List<Post>>> call, Throwable t) {
            }
        });
    }
}

