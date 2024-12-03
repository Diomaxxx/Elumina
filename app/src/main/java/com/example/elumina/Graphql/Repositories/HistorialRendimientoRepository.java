package com.example.elumina.Graphql.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.elumina.Graphql.Models.Entity.HistorialRendimiento;
import com.example.elumina.Graphql.Service.ApiService;
import com.example.elumina.Graphql.Service.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialRendimientoRepository {
    private ApiService apiService;
    private Context context;

    public HistorialRendimientoRepository(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getHistorialByCorreo(String correo, final HistorialRendimientoCallback callback) {
        // Obtener el token de SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);

        Log.d("HistorialRepo", "Correo: " + correo);

        if (token == null) {
            callback.onError("Token no encontrado");
            return;
        }

        // Define la consulta GraphQL con el correo como parámetro
        String query = "{ \"query\": " +
                "\"query { " +
                "historialByCorreo(correo: \\\""+correo+"\\\") { " +
                "consumoRed " +
                "consumoTotal " +
                "fecharegistro " +
                "generacion " +
                "id " +
                "rendimientoid " +
                "} " +
                "}\" " +
                "}";

        Call<String> call = apiService.executeQueryWithAuth(query, "Bearer " + token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Log.d("GraphQLResponse", "Respuesta completa: " + response.body());
                        // Parsear la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());

                        if (jsonResponse.has("errors")) {
                            String errorMessage = jsonResponse.getJSONArray("errors").getJSONObject(0).getString("message");
                            callback.onError("Error en la consulta: " + errorMessage);
                        } else {
                            // Procesar los datos de la respuesta
                            JSONObject data = jsonResponse.getJSONObject("data");
                            JSONArray historialArray = data.getJSONArray("historialByCorreo");

                            // Crear una lista para los objetos HistorialRendimiento
                            List<HistorialRendimiento> historialList = new ArrayList<>();

                            // Recorrer el array de historial y mapear los datos a objetos
                            for (int i = 0; i < historialArray.length(); i++) {
                                JSONObject historialData = historialArray.getJSONObject(i);

                                // Crear el objeto HistorialRendimiento y mapear los datos
                                HistorialRendimiento historial = new HistorialRendimiento();
                                historial.setGeneracion(historialData.getDouble("generacion"));
                                historial.setConsumoRed(historialData.getDouble("consumoRed"));
                                historial.setConsumoTotal(historialData.getDouble("consumoTotal"));
                                historial.setFechaRegistro(historialData.getString("fecharegistro"));
                                historial.setId(historialData.getString("id"));
                                historial.setRendimientoId(historialData.getString("rendimientoid"));

                                // Agregar el objeto a la lista
                                historialList.add(historial);
                            }

                            // Pasar la lista de historial al callback
                            callback.onSuccess(historialList);

                        }
                    } catch (Exception e) {
                        callback.onError("Error al parsear JSON: " + e.getMessage());
                    }
                } else {
                    callback.onError("Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    // Interfaz para pasar el resultado de la consulta al fragmento o actividad
    public interface HistorialRendimientoCallback {
        void onSuccess(List<HistorialRendimiento> historial);
        void onError(String error);
    }
}