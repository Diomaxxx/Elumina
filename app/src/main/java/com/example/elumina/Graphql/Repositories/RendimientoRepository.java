package com.example.elumina.Graphql.Repositories;

import android.util.Log;

import com.example.elumina.Graphql.Models.Entity.Rendimiento;
import com.example.elumina.Graphql.Service.ApiClient;
import com.example.elumina.Graphql.Service.ApiService;
import com.example.elumina.Graphql.Service.GraphQLCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RendimientoRepository {
    private ApiService apiService;

    public RendimientoRepository() {
        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
    }

    public void getRendimientoBySistema(String sistemaId, String token, GraphQLCallback<Rendimiento> callback) {
        String query = "{ \"query\": " +
                "\"query { " +
                "rendimientoBySistema(id: \\\"" + sistemaId + "\\\") { " +
                "consumoRed " +
                "consumoTotal " +
                "generacion " +
                "id " +
                "sistemaid " +
                "} " +
                "}\" " +
                "}";
        String authorization = "Bearer " + token; // Adding the Bearer prefix

        // Agregar el token de autorización a los headers
        apiService.executeQueryWithAuth(query, authorization).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Log.d("GraphQLResponse", "Respuesta completa: " + response.body());
                        JSONObject jsonResponse = new JSONObject(response.body());

                        if (jsonResponse.has("errors")) {
                            String errorMessage = jsonResponse.getJSONArray("errors").getJSONObject(0).getString("message");
                            callback.onError("Error en la consulta: " + errorMessage);
                        } else {
                            // Procesar los datos de la respuesta
                            JSONObject data = jsonResponse.getJSONObject("data");
                            JSONObject rendimientoData = data.getJSONObject("rendimientoBySistema");  // Get the object directly, not array

                            // Create a new Rendimiento object and map the response data
                            Rendimiento objRendimiento = new Rendimiento();
                            objRendimiento.setGeneracion(rendimientoData.getDouble("generacion"));
                            objRendimiento.setConsumoRed(rendimientoData.getDouble("consumoRed"));
                            objRendimiento.setConsumoTotal(rendimientoData.getDouble("consumoTotal"));
                            objRendimiento.setId(rendimientoData.getString("id"));
                            objRendimiento.setSistemaId(rendimientoData.getString("sistemaid"));


                            callback.onSuccess(objRendimiento); // Devuelve la lista de objetos rendimiento
                        }
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
