package com.example.androidapplication.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.adapters.CommentsListAdapter;
import com.example.androidapplication.databinding.ActivityCommentBinding;
import com.example.androidapplication.viewmodels.CommentsViewModel;
import com.example.androidapplication.viewmodels.UsersViewModel;

public abstract class CommentActivity extends AppCompatActivity implements CommentsListAdapter.OnCommentItemClickListener {

    private int postId;
    private int userId;
    private ActivityCommentBinding binding;
    private CommentsListAdapter adapter;
    UsersViewModel usersViewModel;
    CommentsViewModel commentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);}}
        /*
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);

        postId = getIntent().getIntExtra("postID", -1);
        userId = getIntent().getIntExtra("userID", -1);
        RecyclerView lstComments = binding.lstComments;
        adapter = new CommentsListAdapter(this, this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));
        adapter.setComments(commentsViewModel.get(postId));

        binding.btnAdd.setOnClickListener(v -> {
            String userInput = binding.etContent.getText().toString();
            if (!userInput.isEmpty()) {
                Comment comment = new Comment(
                        usersViewModel.get(userId).getNickname(),
                        usersViewModel.get(userId).getImage(),
                        userInput, postId);
                commentsViewModel.add(comment);
                adapter.setComments(commentsViewModel.get(postId));
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                binding.etContent.setText("");
            }
        });
    }

   //TODO check behaviour
    @Override
   public void onEditClick(int position, String content) {
        Comment comment = commentsViewModel.get(postId).get(position);
        comment.setContent(content);
        commentsViewModel.update(comment);
        adapter.notifyItemChanged(position);
    }
    //TODO check behaviour
    @Override
    public void onDeleteClick(int position) {
        Comment comment = commentsViewModel.get(postId).get(position);
        commentsViewModel.delete(comment);
        adapter.notifyItemRemoved(position);
    }

         */
