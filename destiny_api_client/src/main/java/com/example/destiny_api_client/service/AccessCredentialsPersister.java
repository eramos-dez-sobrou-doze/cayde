package com.example.destiny_api_client.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.destiny_api_client.auth.AccessToken;
import com.google.gson.Gson;

public class AccessCredentialsPersister {
    public static final String DESTINY2_CREDENTIALS_TOKEN = "destiny2.credentials.token";
    public static final String CREDENTIALS_PREFERENCES = "credentials";
    private final Context context;
    private final Gson gson = new Gson();

    public AccessCredentialsPersister(Context context) {
        this.context = context;
    }

    public void persist(final AccessToken tokenResponse) {
        SharedPreferences preferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DESTINY2_CREDENTIALS_TOKEN, gson.toJson(tokenResponse));
        editor.apply();
        editor.commit();
    }

    public AccessToken retrieve() {
        SharedPreferences preferences = context.getSharedPreferences(CREDENTIALS_PREFERENCES, Context.MODE_PRIVATE);
        return gson.fromJson(preferences.getString(DESTINY2_CREDENTIALS_TOKEN, "{}"), AccessToken.class);
    }
}
