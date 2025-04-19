package com.example.androidapplication.repositories;

import android.content.Context;
import android.content.SharedPreferences;

public class SPRepo {

    private final SharedPreferences sp;

    public SPRepo(Context context) {
          sp = context.getSharedPreferences("com.example.androidapplication",Context.MODE_PRIVATE);
    }

    public String getToken() {
        return sp.getString("token", null);
    }

    public void setToken(String token) {
        sp.edit().putString("token", token).apply();
    }

}
