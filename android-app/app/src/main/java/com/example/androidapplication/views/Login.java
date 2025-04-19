package com.example.androidapplication.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapplication.databinding.ActivityLoginBinding;
import com.example.androidapplication.entites.CurrentUser;
import com.example.androidapplication.entites.dto.UserLoginForm;
import com.example.androidapplication.viewmodels.UsersViewModel;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UsersViewModel usersViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        binding.btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, Registration.class);
            startActivity(i);
        });

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            usersViewModel.loginUser(Login.this, new UserLoginForm(username, password))
                    .observe(this, token -> {
                if (token != null) {
                    LiveData<CurrentUser> user = usersViewModel.getCurrentUser();
                    Intent Feed = new Intent(this, Main.class);
                    startActivity(Feed);
                    finish();
                } else {
                    binding.tvMessage.setText("There is no user account matching the provided username and password.");
                }
            });
        });
    }
}