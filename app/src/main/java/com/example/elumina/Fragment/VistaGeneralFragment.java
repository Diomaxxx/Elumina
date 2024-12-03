package com.example.elumina.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.elumina.Graphql.Models.Entity.Cliente;
import com.example.elumina.Graphql.Models.Entity.Rendimiento;
import com.example.elumina.Graphql.Models.Entity.Sistema;
import com.example.elumina.Graphql.Repositories.ClienteRepository;
import com.example.elumina.Graphql.Repositories.RendimientoRepository;
import com.example.elumina.Graphql.Repositories.SistemaRepository;
import com.example.elumina.Graphql.Service.GraphQLCallback;
import com.example.elumina.R;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VistaGeneralFragment extends Fragment {
    private TextView GeneracionActual, ConsumoRedActual, ConsumoTotalActual, CuotaActual, DeficitActual, AutoconsumoActual;

    private ScheduledExecutorService scheduler;
    private Handler handler;
    private RendimientoRepository rendimientoRepository;
    private ClienteRepository clienteRepository;
    private SistemaRepository sistemaRepository;
    private String token;


    public VistaGeneralFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vista_general, container, false);

        GeneracionActual = view.findViewById(R.id.tvGeneracionActual);
        ConsumoRedActual = view.findViewById(R.id.tvConsumoRedActual);
        ConsumoTotalActual = view.findViewById(R.id.tvConsumoTotalActual);
        CuotaActual = view.findViewById(R.id.tvCuotaAutarquicaActual);
        DeficitActual = view.findViewById(R.id.tvDeficitActual);
        AutoconsumoActual = view.findViewById(R.id.tvAutoConsumoActual);

        handler = new Handler(Looper.getMainLooper());
        scheduler = Executors.newScheduledThreadPool(1);

        rendimientoRepository = new RendimientoRepository();
        clienteRepository = new ClienteRepository();
        sistemaRepository = new SistemaRepository();


        token = getContext().getSharedPreferences( "user_prefs", MODE_PRIVATE).getString("token", null);


        getData();

        return view;
    }

    private void getData() {

        clienteRepository.obtenerClienteDesdeToken(token, new GraphQLCallback<Cliente>() {
            @Override
            public void onSuccess(Cliente cliente) {

                Log.d("Cliente", "Cliente recibido: " + cliente.getId());
                getSistema(cliente.getId());


            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ErrorCliente", "Error al obtener cliente: " + errorMessage);
            }
        });
    }
    public void getSistema(String clienteId) {
        Log.d("Sistema", "Cliente ID: " + clienteId);

        sistemaRepository.getSistemaByClienteId(clienteId, token, new GraphQLCallback<Sistema>() {
            @Override
            public void onSuccess(Sistema sistema) {
                Log.d("Sistema", "Sistema recibido: " + sistema.getNombrePlanta());
                getContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
                        .edit()
                        .putString("sistemaId", sistema.getId())
                        .apply();
                startPeriodicUpdates();


            }

            @Override
            public void onError(String errorMessage) {
                // Si hubo un error al obtener el sistema, manejarlo aquí
                Log.e("ErrorSistema", "Error al obtener sistema: " + errorMessage);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detener el scheduler cuando la vista sea destruida
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }

    private void startPeriodicUpdates() {
        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                performGraphQLQuery();
            }
        }, 0, 5, TimeUnit.SECONDS);  // Realizar la consulta cada 5 segundos
    }

    private void performGraphQLQuery() {

        String sistemaId = getContext().getSharedPreferences( "user_prefs", MODE_PRIVATE).getString("sistemaId", null);

        if (token != null) {
            RendimientoRepository rendimientoRepository = new RendimientoRepository();
            rendimientoRepository.getRendimientoBySistema(sistemaId, token, new GraphQLCallback<Rendimiento>() {
                @Override
                public void onSuccess(Rendimiento result) {
                    // Aquí puedes manejar la lista de objetos Rendimiento
                    Log.d("Rendimiento", "Id: " + result.getId());
                    Log.d("Rendimiento", "Generación: " + result.getGeneracion());
                    String generacion = String.format("%.2f", result.getGeneracion() * 1000) + " W";
                    String consumoRed = String.format("%.2f", result.getConsumoRed()) + " kW";
                    String consumoTotal = String.format("%.2f", result.getConsumoTotal()) + " kW";
                    double cuotaValue = (result.getGeneracion() / result.getConsumoTotal()) * 100;
                    String cuota = String.format("%.2f", cuotaValue) + " %";
                    String deficit = String.format("%.2f", result.getConsumoRed()) + " kW";
                    double autoconsumoValue = 0;
                    if (result.getGeneracion() <= result.getConsumoTotal()) {
                        autoconsumoValue = 100;
                    } else {
                        autoconsumoValue = (result.getGeneracion() / result.getConsumoTotal()) * 100;
                    }
                    String autoconsumo = String.format("%.2f", autoconsumoValue) + " %";

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            GeneracionActual.setText(generacion);
                            ConsumoRedActual.setText(consumoRed);
                            ConsumoTotalActual.setText(consumoTotal);
                            CuotaActual.setText(cuota);
                            DeficitActual.setText(deficit);
                            AutoconsumoActual.setText(autoconsumo);
                        }
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    // Manejo de errores
                    Log.e("Rendimiento", "Error: " + errorMessage);
                }
            });
        } else {
            // El token no está disponible, redirigir al login
            Log.e("Rendimiento", "Token no disponible");
        }

    }
}
