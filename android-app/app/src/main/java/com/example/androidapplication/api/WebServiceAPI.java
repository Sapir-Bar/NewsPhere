package com.example.androidapplication.api;

import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreatePostForm;
import com.example.androidapplication.entites.dto.CreateUserForm;
import com.example.androidapplication.entites.dto.EditPostForm;
import com.example.androidapplication.entites.dto.Token;
import com.example.androidapplication.entites.dto.UserLoginForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceAPI {

    @GET("posts")
    Call<ServerResponse<List<Post>>> getPosts(@Header("Authorization") String token);

    @GET("users/{id}/posts")
    Call<ServerResponse<List<Post>>> getFriendPosts(@Header("Authorization") String token, @Path("id") String id);

    @PUT("users/toggle-friend/{id}")
    Call<ServerResponse<CurrentUser>> toggleFriend(@Header("Authorization") String token, @Path("id") String id);

    @POST("users/{id}/posts")
    Call<ServerResponse<Post>> createPost(@Header("Authorization") String token, @Path("id") String id, @Body CreatePostForm createData);

    @DELETE("users/posts/{pid}")
    Call<ServerResponse<User>> deletePost(@Header("Authorization") String token, @Path("pid") String id);

    @PUT("users/posts/{pid}")
    Call<ServerResponse<Post>> updatePost(@Header("Authorization") String token, @Path("pid") String id, @Body EditPostForm editData);

    @GET("users")
    Call<ServerResponse<List<User>>> getUsers();

    @POST("users")
    Call<ServerResponse<User>> addUser(@Body CreateUserForm createData);

    //The @Path("id") annotation indicates that the id parameter value
    //should be substituted into the {id} placeholder in the URL.
    @GET("users/{id}")
    Call<ServerResponse<CurrentUser>> getCurrentUser(@Header("Authorization") String token, @Path("id") int id);

    @POST("tokens")
    Call<ServerResponse<Token>> loginUser(@Body UserLoginForm loginData);
}
