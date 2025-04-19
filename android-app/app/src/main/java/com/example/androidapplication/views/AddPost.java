package com.example.androidapplication.views;

import static com.example.androidapplication.views.EditPost.IMAGE_PICK_REQUEST_CODE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.adapters.ImageUtils;
import com.example.androidapplication.databinding.ActivityAddPostBinding;
import com.example.androidapplication.entites.dto.CreatePostForm;
import com.example.androidapplication.viewmodels.PostsViewModel;
import com.example.androidapplication.viewmodels.UsersViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;


public class AddPost extends AppCompatActivity {
    private ActivityAddPostBinding binding;
    Uri chosenImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PostsViewModel postsViewModel = MyApplication.postsViewModel;
        UsersViewModel usersViewModel = MyApplication.usersViewModel;
        AtomicBoolean isPublished = new AtomicBoolean(false);

        usersViewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                binding.tvAuthorNickname.setText(user.getNickname());
                binding.ivAuthorImage.setImageBitmap(ImageUtils.decodeBase64ToBitmap(user.getImage()));
                //Picasso.get().load(Uri.parse(user.getImage())).into(binding.ivAuthorImage);
            }
        });

        postsViewModel.getPostCreationResult().observe(this, result -> {
            if (result && isPublished.get()) {
                binding.tvProhibitedMessage.setVisibility(View.INVISIBLE);
                Intent MainIntent = new Intent(this, Main.class);
                startActivity(MainIntent);
                finish();
            } else if (isPublished.get()) {
                binding.tvProhibitedMessage.setVisibility(View.VISIBLE);
            }
            });
        binding.btnImageButton.setOnClickListener(v -> {
            // Use an Intent.ACTION_PICK to choose an image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
        });

        binding.btnPublish.setOnClickListener(v -> {
            if(!binding.etContent.getText().toString().isEmpty()) {
                String content = binding.etContent.getText().toString().trim();
                String currentDateTime = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US).format(new Date());
                String base64Image = (chosenImage != null) ? ImageUtils.encodeUriToBase64(this, chosenImage, 300, 300, 10) : null;
                CreatePostForm form = new CreatePostForm( content, base64Image, currentDateTime);
                postsViewModel.add(form);
                isPublished.set(true);
            } else {
                binding.tvErrorMessage.setVisibility(View.VISIBLE);
            }
        });

        binding.btnBack.setOnClickListener( v -> {
            binding.tvProhibitedMessage.setVisibility(View.INVISIBLE);
            binding.tvErrorMessage.setVisibility(View.INVISIBLE);
            Intent MainIntent = new Intent(this, Main.class);
            startActivity(MainIntent);
            finish();
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
                chosenImage = data.getData();
                Picasso.get()
                        .load(chosenImage)
                        .resize(300, 300)
                        .centerCrop()
                        .into(binding.ivPic);
                binding.ivPic.setVisibility(View.VISIBLE);
            }
        }

    }