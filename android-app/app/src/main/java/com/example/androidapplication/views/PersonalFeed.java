package com.example.androidapplication.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.adapters.ImageUtils;
import com.example.androidapplication.adapters.SimplePostsListAdapter;
import com.example.androidapplication.databinding.ActivityPersonalFeedBinding;
import com.example.androidapplication.viewmodels.PostsViewModel;
import com.example.androidapplication.viewmodels.UsersViewModel;

public class PersonalFeed extends AppCompatActivity {
    private ActivityPersonalFeedBinding binding;
    private UsersViewModel usersViewModel;
    private PostsViewModel postsViewModel;
    SimplePostsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityPersonalFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usersViewModel = MyApplication.usersViewModel;
        postsViewModel = MyApplication.postsViewModel;
        String idOther = getIntent().getStringExtra("idOther");

        usersViewModel.reload();
        usersViewModel.get(idOther).observe(this, user -> {
            if (user != null) {
                binding.tvUsername.setText(user.getUsername());
                binding.imageProfile.setImageBitmap(ImageUtils.decodeBase64ToBitmap(user.getImage()));
            }
        });

        RecyclerView lstPosts = binding.lstPosts;
        SimplePostsListAdapter adapter = new SimplePostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lstPosts.getContext(), LinearLayoutManager.VERTICAL);
        lstPosts.addItemDecoration(dividerItemDecoration);

        usersViewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                if(user.getFriends().contains(idOther)) {
                    String removeFriendship = "Unfriend";
                    binding.btnFriendReq.setText(removeFriendship);
                    postsViewModel.reloadFriendPosts(idOther);
                    postsViewModel.getFriendPosts().observe(this, posts -> {
                            if (posts != null) {
                                adapter.setPosts(posts);
                                adapter.notifyDataSetChanged();
                            }
                    });
                } else {
                    String createFriendship = "Send friend request";
                    binding.btnFriendReq.setText(createFriendship);
                }
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}