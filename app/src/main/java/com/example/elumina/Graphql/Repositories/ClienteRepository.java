package com.example.elumina.Graphql.Repositories;

import android.util.Log;

import com.example.elumina.Graphql.Models.Entity.Cliente;
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

    public void obtenerClienteDesdeToken(String token, GraphQLCallback<Cliente> callback) {
        String query = "{ \"query\": " +
                "\"query { " +
                "obtenerClienteDesdeToken(token: \\\""+token+"\\\") { " +
                "activo, apellido, correo, direccion, id, nombre, password, telefono " +
                "} " +
                "}\" " +
                "}";

        String authorization = "Bearer " + token;

        apiService.executeQueryWithAuth(query, authorization).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Log.d("GraphQLResponse", "Respuesta completa: " + response.body());
                        JSONObject jsonResponse = new JSONObject(response.body());

                        if (jsonResponse.has("errors")) {
                            String errorMessage = jsonResponse.getJSONArray("errors").getJSONObject(0).getString("message");
                            callback.onError("Error al obtener cliente: " + errorMessage);
                        } else {
                            JSONObject data = jsonResponse.getJSONObject("data");
                            JSONObject clienteData = data.getJSONObject("obtenerClienteDesdeToken");

                            // Crear un nuevo objeto Cliente y mapear los datos
                            Cliente cliente = new Cliente();
                            cliente.setActivo(clienteData.getBoolean("activo"));
                            cliente.setApellido(clienteData.getString("apellido"));
                            cliente.setCorreo(clienteData.getString("correo"));
                            cliente.setDireccion(clienteData.getString("direccion"));
                            cliente.setId(clienteData.getString("id"));
                            cliente.setNombre(clienteData.getString("nombre"));
                            cliente.setPassword(clienteData.getString("password"));
                            cliente.setTelefono(clienteData.getString("telefono"));

                            callback.onSuccess(cliente);
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
