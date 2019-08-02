package com.example.destiny_api_client.login;

import com.example.destiny_api_client.auth.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("/Platform/App/OAuth/token/")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @FormUrlEncoded
    @POST("/platform/app/oauth/token/")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("/Platform/App/OAuth/token/")
    Call<AccessToken> getAccessTokenWithCustomHeader(
            @Field("code") String code,
            @Field("grant_type") String grantType,
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("/platform/app/oauth/token/")
    //@POST("/Platform/App/OAuth/token/")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("grant_type") String grantType
    );
}
