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
import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreateUserForm;
import com.example.androidapplication.entites.dto.Token;
import com.example.androidapplication.entites.dto.UserLoginForm;
import com.example.androidapplication.repositories.SPRepo;
import com.example.androidapplication.repositories.UserDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private MutableLiveData<List<User>> userListData;
    private LiveData<CurrentUser> currentUserLiveData;
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<List<User>> userListData, UserDao dao) {
        this.userListData = userListData;
        currentUserLiveData = dao.getCurrentUser(); // point on the room. when the room changes, the liveData change accordingly
        this.dao = dao;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(Post.class, new PostTypeAdapter())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public LiveData<CurrentUser> getCurrentUser(Context context) {
        SPRepo sp = new SPRepo(context);
        String token;
        if (sp.getToken() != null) {
            token = sp.getToken().toString();
        } else token = null;
        if(token == null) return currentUserLiveData; // do the server request only if token != null
        String s = "Bearer " + token;
        Call<ServerResponse<CurrentUser>> call = webServiceAPI.getCurrentUser(s, 0);
        call.enqueue(new Callback<ServerResponse<CurrentUser>>() {
            @Override
            public void onResponse(Call<ServerResponse<CurrentUser>> call, Response<ServerResponse<CurrentUser>> response) {
                ServerResponse<CurrentUser> usersResponse = response.body();
                new Thread(() -> {
                    dao.deleteCurrentUser();
                    dao.upserCurrentUser(usersResponse.getData());
                }).start();
            }
            @Override
            public void onFailure(Call<ServerResponse<CurrentUser>> call, Throwable t) {
            }
        });
        return currentUserLiveData;
    }

    public LiveData<CurrentUser> toggleFriend (Context context, String idOther) {
        SPRepo sp = new SPRepo(context);
        String token;
        if (sp.getToken() != null) {
            token = sp.getToken().toString();
        } else token = null;
        if(token == null) return currentUserLiveData; // do the server request only if token != null
        String s = "Bearer " + token;
        Call<ServerResponse<CurrentUser>> call = webServiceAPI.toggleFriend(s, idOther);
        call.enqueue(new Callback<ServerResponse<CurrentUser>>() {
            @Override
            public void onResponse(Call<ServerResponse<CurrentUser>> call, Response<ServerResponse<CurrentUser>> response) {
                ServerResponse<CurrentUser> usersResponse = response.body();
                new Thread(() -> {
                    dao.deleteCurrentUser();
                    dao.upserCurrentUser(usersResponse.getData());
                }).start();
            }
            @Override
            public void onFailure(Call<ServerResponse<CurrentUser>> call, Throwable t) {
            }
        });
        return currentUserLiveData;
    }

    public void reload() {
        Call<ServerResponse<List<User>>> call = webServiceAPI.getUsers();
        call.enqueue(new Callback<ServerResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ServerResponse<List<User>>> call, Response<ServerResponse<List<User>>> response) {
                ServerResponse<List<User>> usersResponse = response.body();
                new Thread(() -> {
                    dao.clear();
                    dao.insert(usersResponse.getData());
                    userListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<ServerResponse<List<User>>> call, Throwable t) {
            }
        });
    }

    public LiveData<Token> loginUser(Context context, UserLoginForm form){
        SPRepo sp = new SPRepo(context);
        MutableLiveData<Token> loginResult = new MutableLiveData<>();
        Call<ServerResponse<Token>> call = webServiceAPI.loginUser(form);
        call.enqueue(new Callback<ServerResponse<Token>>(){
            @Override
            public void onResponse(Call<ServerResponse<Token>> call, Response<ServerResponse<Token>> response) {
                if (response.body().getStatus() == 200) {
                    Token token = response.body().getData();
                    sp.setToken(token.getToken());
                    loginResult.setValue(token);
                } else { loginResult.setValue(null); }
            }
            @Override
            public void onFailure(Call<ServerResponse<Token>> call, Throwable t) {
                loginResult.setValue(null);
            }
        });
        return loginResult;
    }

    public void add(CreateUserForm form){
        Call<ServerResponse<User>> call = webServiceAPI.addUser(form);
        call.enqueue(new Callback<ServerResponse<User>>() {
            @Override
            public void onResponse(Call<ServerResponse<User>> call, Response<ServerResponse<User>> response) {
                ServerResponse<User> usersResponse = response.body();
                new Thread(() -> {
                    dao.insert(usersResponse.getData());
                    userListData.postValue(dao.index());
                }).start();
            }
            @Override
            public void onFailure(Call<ServerResponse<User>> call, Throwable t) { }
        });
    }

    public LiveData<User> get(String userId) { return dao.get(userId); }
    public void delete(User user){}
    public void update(User user){}

}
