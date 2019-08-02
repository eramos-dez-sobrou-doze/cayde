package com.example.destiny_api_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.destiny_api_client.auth.AccessToken;
import com.example.destiny_api_client.factory.ServiceGenerator;
import com.example.destiny_api_client.login.LoginService;

import java.io.IOException;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = "[LOGIN_FORM]";
    // you should either define client id and secret as constants or in string resources
    private String clientId = null;
    private String clientSecret = null;
    private String authFormUrl = null;
    private String redirectUri = "your://redirecturi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clientId = getResources().getString(R.string.client_id);
        clientSecret = getResources().getString(R.string.client_secret);
        authFormUrl = getResources().getString(R.string.api_auth_login_form);
        //redirectUri = "http://localhost";

        Button loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginFormUrl = String.format("%s?client_id=%s&response_type=code&redirect_uri=%s", authFormUrl, clientId, Uri.encode(redirectUri));

                Log.d(LOG_TAG, String.format("form_url: %s", loginFormUrl));

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(loginFormUrl)
                );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        Log.d(LOG_TAG, String.format("uri: %s", uri != null ? uri.toString() : "NULL"));
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            Log.d(LOG_TAG, String.format("code: %s", code));
            if (code != null) {
                ServiceGenerator.createService(LoginService.class, clientId, clientSecret)
                        .getAccessToken(code, "authorization_code", clientId, clientSecret)
                        //.getAccessToken(code, "authorization_code", getResources().getString(R.string.client_id))
                        .enqueue(new Callback<AccessToken>() {
                            @Override
                            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                Log.d(LOG_TAG, String.format("auth response: %s", response.toString()));
                            }

                            @Override
                            public void onFailure(Call<AccessToken> call, Throwable t) {
                                Log.e(LOG_TAG, String.format("auth response: %s", t.getMessage()), t);
                            }
                        });
            } else if (uri.getQueryParameter("error") != null) {
                Log.e(LOG_TAG, String.format("error: %s", uri.getQueryParameter("error")));
            }
        }
    }
}
