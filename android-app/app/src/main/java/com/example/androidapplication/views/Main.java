package com.example.androidapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.NightModeManager;
import com.example.androidapplication.adapters.ImageUtils;
import com.example.androidapplication.adapters.PostsListAdapter;
import com.example.androidapplication.databinding.ActivityMainBinding;
import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.viewmodels.PostsViewModel;
import com.example.androidapplication.viewmodels.UsersViewModel;

import java.util.Collections;
import java.util.Objects;

public class Main extends AppCompatActivity implements PostsListAdapter.OnPostItemClickListener {

    private static final int EDIT_REQUEST_CODE = 1;
    private static final int ADD_REQUEST_CODE = 2;
    private ActivityMainBinding binding;
    private PostsListAdapter adapter;
    private PostsViewModel postsViewModel;
    private UsersViewModel usersViewModel;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postsViewModel = MyApplication.postsViewModel;
        usersViewModel = MyApplication.usersViewModel;

        Switch switcher = binding.switcher;
        NightModeManager.applyNightMode(this, switcher);

        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lstPosts.getContext(), LinearLayoutManager.VERTICAL);
        lstPosts.addItemDecoration(dividerItemDecoration);
        usersViewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                currentUser = user;
                binding.tvUsername.setText("Welcome back, " + user.getUsername());
                binding.imageProfile.setImageBitmap(ImageUtils.decodeBase64ToBitmap(user.getImage()));
                //Picasso.get().load(Uri.parse(user.getImage())).into(binding.imageProfile);
            }
        });

        binding.btnLogout.setOnClickListener(v -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
            finish();
        });

        binding.btnAdd.setOnClickListener(v -> {
            Intent AddIntent = new Intent(this, AddPost.class);
            startActivity(AddIntent);
            finish();
        });

        binding.refreshLayout.setOnRefreshListener(() -> {
            postsViewModel.reload();
        });

        postsViewModel.getAll().observe(this, posts -> {
            Collections.sort(posts, (post1, post2) -> post2.getDate().compareTo(post1.getDate()));
            adapter.setPosts(posts);
            binding.refreshLayout.setRefreshing(false);
        });
    }

    public void onCommentClick(Post post) {  }


    public void onEditClick(Post current, int position) {
        if (currentUser != null && current.getAuthor().get_id().equals(currentUser.get_id())) {
            Intent editIntent = new Intent(this, EditPost.class);
            editIntent.putExtra("postId", current.get_id());
            editIntent.putExtra("position", position);
            startActivityForResult(editIntent, EDIT_REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "You lack permission to update this post.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteClick(Post current, int position) {
        if (currentUser != null && Objects.equals(current.getAuthor().get_id(), currentUser.get_id())) {
            postsViewModel.delete(current);
            adapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(getApplicationContext(), "You lack permission to delete this post.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onProfileClick(Post current) {
        Intent personalFeed = new Intent(this, PersonalFeed.class);
        personalFeed.putExtra("idOther", current.getAuthor().get_id());
        startActivity(personalFeed);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra("position", -1);
            adapter.notifyItemChanged(position);
            }
        }
    }
