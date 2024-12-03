package com.example.elumina.Graphql.Repositories;

import android.util.Log;

import com.example.elumina.Graphql.Models.Entity.Sistema;
import com.example.elumina.Graphql.Service.ApiClient;
import com.example.elumina.Graphql.Service.ApiService;
import com.example.elumina.Graphql.Service.GraphQLCallback;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SistemaRepository {
    private ApiService apiService;

    public SistemaRepository() {
        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
    }

    public void getSistemaByClienteId(String clienteId, String token, GraphQLCallback<Sistema> callback) {
        // Definir la consulta GraphQL
        String query = "{ \"query\": " +
                "\"query { " +
                "sistemaFotovoltaicoByClienteId(id: \\\"" + clienteId + "\\\") { " +
                "activo " +
                "capacidadInversor " +
                "capacidadTotal " +
                "clienteid " +
                "fechainstalacion " +
                "id " +
                "lat_ubicacion " +
                "lon_ubicacion " +
                "modeloInversor " +
                "nombrePlanta " +
                "numeroPaneles " +
                "provedorid " +
                "} " +
                "}\" " +
                "}";

        // Agregar el token de autorización a los headers
        String authorization = "Bearer " + token; // Token con el prefijo "Bearer"

        // Realizar la consulta a la API
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
                            JSONObject sistemaData = data.getJSONObject("sistemaFotovoltaicoByClienteId");  // Obtener el objeto del sistema

                            // Crear un objeto Sistema y mapear los datos de la respuesta
                            Sistema sistema = new Sistema();
                            sistema.setActivo(sistemaData.getBoolean("activo"));
                            sistema.setCapacidadInversor(sistemaData.getString("capacidadInversor"));
                            sistema.setCapacidadTotal(sistemaData.getString("capacidadTotal"));
                            sistema.setClienteId(sistemaData.getString("clienteid"));
                            sistema.setFechaInstalacion(sistemaData.getString("fechainstalacion"));
                            sistema.setId(sistemaData.getString("id"));
                            sistema.setLatUbicacion(sistemaData.getDouble("lat_ubicacion"));
                            sistema.setLonUbicacion(sistemaData.getDouble("lon_ubicacion"));
                            sistema.setModeloInversor(sistemaData.getString("modeloInversor"));
                            sistema.setNombrePlanta(sistemaData.getString("nombrePlanta"));
                            sistema.setNumeroPaneles(sistemaData.getString("numeroPaneles"));
                            sistema.setProveedorId(sistemaData.getString("provedorid"));

                            // Llamar al callback con el objeto Sistema
                            callback.onSuccess(sistema);
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
