package com.example.androidapplication.views;

import static com.example.androidapplication.views.EditPost.IMAGE_PICK_REQUEST_CODE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.adapters.ImageUtils;
import com.example.androidapplication.databinding.ActivityRegistrationBinding;
import com.example.androidapplication.entites.User;
import com.example.androidapplication.entites.dto.CreateUserForm;
import com.example.androidapplication.viewmodels.UsersViewModel;
import com.squareup.picasso.Picasso;


public class Registration extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private Uri chosenImage;
    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usersViewModel = MyApplication.usersViewModel;
        usersViewModel.getAll().observe(this, users -> {
            if (users != null) {
                // Handle user list changes here if needed
            }
        });
        binding.btnImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
        });

        binding.btnConfirmRegistration.setOnClickListener(v -> {

            usersViewModel.reload();

            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String confirmedPassword = binding.etConfirmPassword.getText().toString().trim();
            String nickname = binding.etNickname.getText().toString().trim();

            boolean filledTest = (!username.isEmpty()
                    && !password.isEmpty()
                    && !confirmedPassword.isEmpty()
                    && !nickname.isEmpty()
                    && chosenImage != null);

            boolean confirmPasswordTest = password.equals(confirmedPassword);

            boolean contentPasswordTest = password.matches(".*\\d.*") &&
                    (password.matches(".*[A-Z].*") || password.matches(".*[a-z].*"));

            boolean passwordLenTest = password.length() >= 8;

            // Check if username already exists
            boolean overlapUsernameTest = true;
            if (usersViewModel.getAll().getValue() != null) {
                for (User user : usersViewModel.getAll().getValue()) {
                    if (user.getUsername().equals(username)) {
                        overlapUsernameTest = false; //There is overlap
                        break;
                    }
                }
            }

            // Display appropriate error message or proceed with registration
            if (!filledTest) {
                binding.tvMessage.setText("Please fill in all fields, include profile image");
            } else if (!overlapUsernameTest) {
                binding.tvMessage.setText("Sorry, the chosen username is already in use. Please select a different one for your registration.");
            } else if (!contentPasswordTest || !passwordLenTest) {
                binding.tvMessage.setText("The password has a minimum length of 8 and must include at least one character from the group {[A-Z], [a-z]} and a digit");
            } else if (!confirmPasswordTest) {
                binding.tvMessage.setText("Unsuccessful password confirmation");
            } else {
                String base64Image = ImageUtils.encodeUriToBase64(this, chosenImage, 50, 50, 10);
                CreateUserForm form = new CreateUserForm(username, password, nickname, base64Image);
                usersViewModel.add(form);
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent Login = new Intent(this, Login.class);
            startActivity(Login);
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
                    .into(binding.btnImageButton);
        }
    }
}