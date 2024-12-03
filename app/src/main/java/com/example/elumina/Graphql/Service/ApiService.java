package com.example.elumina.Graphql.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("graphql")
    Call<String> executeQuery(@Body String query);
    @Headers("Content-Type: application/json")
    @POST("graphql")
    Call<String> executeQueryWithAuth(@Body String query, @Header("Authorization") String token);
}
