package com.example.androidapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;

public class NightModeManager {
    public static void applyNightMode(Context context, Switch switcher) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        boolean nightMode = sharedPreferences.getBoolean("night", false);

        if (nightMode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switcher.setOnClickListener(v -> {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("night", !nightMode);
            editor.apply();
        });
    }
}
