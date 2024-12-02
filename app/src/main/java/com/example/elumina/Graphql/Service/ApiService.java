package com.example.elumina.Graphql.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("graphql")
    Call<String> executeRequest(@Body String query);
}
