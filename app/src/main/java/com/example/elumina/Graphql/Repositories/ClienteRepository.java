package com.example.elumina.Graphql.Repositories;

import android.util.Log;

import com.example.elumina.Graphql.Service.ApiClient;
import com.example.elumina.Graphql.Service.ApiService;
import com.example.elumina.Graphql.Service.GraphQLCallback;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClienteRepository {
    private ApiService apiService;
    public ClienteRepository(){
        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
    }

    public void loginCliente(String correo,String password, GraphQLCallback<String> callback) {
        String query = "{ \"query\": " +
                "\"query { " +
                "loginCliente(correo: \\\""+correo+"\\\", password: \\\""+password+"\\\") { " +
                "} " +
                "}\" " +
                "}";

        apiService.executeQuery(query).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Log.d("GraphQLResponse", "Respuesta completa: " + response.body());
                        JSONObject jsonResponse = new JSONObject(response.body());
                        if (jsonResponse.has("errors")) {
                            String errorMessage = jsonResponse.getJSONArray("errors").getJSONObject(0).getString("message");
                            callback.onError("Error en el login: " + errorMessage);
                        } else {
                            callback.onSuccess(response.body());
                        }
                        callback.onSuccess(response.body());
                    } catch (Exception e) {
                        callback.onError("Error al parsear JSON: " + e.getMessage());
                    }
                } else {
                    callback.onError("Error en la respuesta: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError("Error: " + t.getMessage());
            }
        });
    }
}
