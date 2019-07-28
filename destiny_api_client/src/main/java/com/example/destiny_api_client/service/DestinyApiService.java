package com.example.destiny_api_client.service;

import android.app.Activity;
import android.content.Intent;

import com.example.destiny_api_client.LoginActivity;

public class DestinyApiService {

    private final Activity activity;

    public DestinyApiService(Activity activity) {
        this.activity = activity;
    }

    public void login() {
        final Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
    }
}
