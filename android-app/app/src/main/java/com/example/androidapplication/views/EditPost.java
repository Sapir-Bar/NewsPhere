package com.example.androidapplication.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.adapters.ImageUtils;
import com.example.androidapplication.databinding.ActivityEditPostBinding;
import com.example.androidapplication.entites.Post;
import com.example.androidapplication.viewmodels.PostsViewModel;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicBoolean;

public class EditPost extends AppCompatActivity {
    private ActivityEditPostBinding binding;
    public static final int IMAGE_PICK_REQUEST_CODE = 123;
    private PostsViewModel postsViewModel;
    Uri editedImage = null;
    Post current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postsViewModel = MyApplication.postsViewModel;
        AtomicBoolean isPublished = new AtomicBoolean(false);

        int position = getIntent().getIntExtra("position", -1);
        String postId = getIntent().getStringExtra("postId");

        postsViewModel.get(postId).observe(this, post -> {
            if (post != null) {
                current = post;
                // populate the UI elements
                binding.tvAuthorNickname.setText(post.getAuthor().getNickname());
                binding.tvDate.setText(post.getDate());
                binding.etContent.setText(post.getContent());
                binding.ivAuthorImage.setImageBitmap(ImageUtils.decodeBase64ToBitmap(post.getAuthor().getImage()));
                if (post.getImage() != null)
                    binding.ivPic.setImageBitmap(ImageUtils.decodeBase64ToBitmap(post.getImage()));

                /*
                Picasso.get()
                        .load(Uri.parse(post.getAuthor().getImage())).into(binding.ivAuthorImage);
                    Picasso.get().load(Uri.parse(post.getImage())).into(binding.ivPic);
                */
            }
     });

        postsViewModel.getPostUpdateResult().observe(this, result -> {
            if (result && isPublished.get()) {
                    binding.tvProhibitedMessage.setVisibility(View.INVISIBLE);
                    Intent Feed = new Intent();
                    Feed.putExtra("position", position);
                    setResult(RESULT_OK, Feed);
                    finish();
            } else if (isPublished.get()) {
                    binding.tvProhibitedMessage.setVisibility(View.VISIBLE);
            }
        });

        binding.btnEditPostImage.setOnClickListener(v -> {
            // Use an Intent.ACTION_PICK to choose an image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
        });

        // When the user saves changes in EditActivity set the result and finish the EditActivity
    binding.btnSave.setOnClickListener(v -> {

        Post copy = new Post(current);
        String content = binding.etContent.getText().toString().trim();
        copy.setContent(content);
        if (editedImage != null) {
            String base64Image = ImageUtils.encodeUriToBase64(this, editedImage, 300, 300, 100);
            copy.setImage(base64Image);
        }
        postsViewModel.update(copy);
        isPublished.set(true);
    });

    binding.btnBack.setOnClickListener(v -> {
        binding.tvProhibitedMessage.setVisibility(View.INVISIBLE);
        finish();
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            editedImage = data.getData();
            Picasso.get()
                    .load(editedImage)
                    .resize(300, 300)
                    .centerCrop()
                    .into(binding.ivPic);
        }
    }
}