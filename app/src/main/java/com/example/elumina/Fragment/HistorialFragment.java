package com.example.elumina.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.elumina.Graphql.Models.Entity.HistorialRendimiento;
import com.example.elumina.Graphql.Repositories.HistorialRendimientoRepository;
import com.example.elumina.Materiales.ConfiguracionGrafica;
import com.example.elumina.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {
    private LineChart lineChart;
    private HistorialRendimientoRepository historialRendimientoRepository;

    public HistorialFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        // Inicializar el repositorio con el contexto de la actividad
        historialRendimientoRepository = new HistorialRendimientoRepository(getContext());

        // Configurar los gráficos
        lineChart = view.findViewById(R.id.lineChart);
        TextView tvGeneracionConsumo = view.findViewById(R.id.tvGeneracionConsumo);

        // Configura el clic del TextView para mostrar el BottomSheet
        tvGeneracionConsumo.setOnClickListener(v -> {
            showBottomSheet();
        });

        // Recupera el correo del usuario desde SharedPreferences
        String correoUsuario = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE)
                .getString("clienteEmail", null);

        if (correoUsuario != null) {
            // Obtener el historial de rendimiento usando el correo del usuario
            historialRendimientoRepository.getHistorialByCorreo(correoUsuario, new HistorialRendimientoRepository.HistorialRendimientoCallback() {
                @Override
                public void onSuccess(List<HistorialRendimiento> historialList) {
                    // Aquí ya tenemos la lista de objetos HistorialRendimiento
                    if (historialList != null && !historialList.isEmpty()) {
                        // Recorremos la lista de historial y mostramos los datos en consola
//                        for (HistorialRendimiento historial : historialList) {
//                            Log.d("HistorialFragment", "ID: " + historial.getId());
//                            Log.d("HistorialFragment", "Generación: " + historial.getGeneracion());
//                            Log.d("HistorialFragment", "Consumo Red: " + historial.getConsumoRed());
//                            Log.d("HistorialFragment", "Consumo Total: " + historial.getConsumoTotal());
//                            Log.d("HistorialFragment", "Fecha Registro: " + historial.getFechaRegistro());
//                            Log.d("HistorialFragment", "Rendimiento ID: " + historial.getRendimientoId());
//                            Log.d("HistorialFragment", "---------------------------------");

                        ArrayList<Entry> generationEntries = new ArrayList<Entry>();
                        ArrayList<Entry> consumptionEntries = new ArrayList<>();
                        ArrayList<Entry> gridEntries = new ArrayList<>();

                        // Recorremos la lista de historial y llenamos las listas de entradas para la gráfica
                        for (int i = 0; i < historialList.size(); i++) {
                            HistorialRendimiento historial = historialList.get(i);

                            // Usamos la posición del array como el eje X (por ejemplo, el índice i)
                            float xValue = i;

                            // Agregar los valores correspondientes a generación, consumo y consumo de la red
                            generationEntries.add(new Entry(xValue, (float) historial.getGeneracion()));
                            consumptionEntries.add(new Entry(xValue, (float) historial.getConsumoTotal()));
                            gridEntries.add(new Entry(xValue, (float) historial.getConsumoRed()));

                            ConfiguracionGrafica.configurar(lineChart, generationEntries, consumptionEntries, gridEntries);
                        }

                        // Pasamos los datos a la configuración de la gráfica
                        ConfiguracionGrafica.configurar(lineChart, generationEntries, consumptionEntries, gridEntries);
                        }else {
                            Log.d("HistorialFragment", "No se recibieron datos válidos.");
                        }
                    }

                @Override
                public void onError(String error) {
                    // Mostrar un mensaje de error si ocurre algo
                    Log.d("HistorialFragment", "Error: " + error);
                }
            });
        } else {
            Log.d("HistorialFragment", "Correo no encontrado en SharedPreferences.");
        }
        return view;
    }

    // Método para mostrar el BottomSheet
    private void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
