package com.example.elumina.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.elumina.Graphql.Models.Entity.HistorialRendimiento;
import com.example.elumina.Graphql.Repositories.HistorialRendimientoRepository;
import com.example.elumina.Materiales.ConfiguracionGrafica;
import com.example.elumina.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {
    private LineChart lineChart;
    private HistorialRendimientoRepository historialRendimientoRepository;

    // Agregar TextViews y ProgressBar para mostrar la cuota autárquica y su progreso
    private TextView tvGeneracionPromedio;
    private TextView tvConsumoPromedio;
    private TextView tvCuotaAutarquica;
    private ProgressBar pbCuotaAutarquica;
    private TextView tvValorConsumoDirecto;
    private TextView tvCuotaValorConsumoDirecto;
    private TextView tvValorAutoconsumo;
    private TextView tvCuotaValorAutoconsumo;
    private TextView tvValorConsumoRed;
    private TextView tvInyeccionValorRed;
    private TextView tvValorPrevencion;

    public HistorialFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        tvValorConsumoDirecto = view.findViewById(R.id.tvValorConsumoDirecto);
        tvCuotaValorConsumoDirecto = view.findViewById(R.id.tvCuotaValorConsumoDirecto);
        tvValorAutoconsumo = view.findViewById(R.id.tvValorAutoconsumo);
        tvCuotaValorAutoconsumo = view.findViewById(R.id.tvCuotaValorAutoconsumo);
        tvValorConsumoRed = view.findViewById(R.id.tvValorConsumoRed);
        tvInyeccionValorRed = view.findViewById(R.id.tvInyeccionValorRed);
        tvValorPrevencion=view.findViewById(R.id.tvValorPrevencion);

        // Inicializar el repositorio con el contexto de la actividad
        historialRendimientoRepository = new HistorialRendimientoRepository(getContext());

        // Configurar los gráficos
        lineChart = view.findViewById(R.id.lineChart);

        // Inicializar los TextViews y ProgressBar para cuota autárquica
        tvGeneracionPromedio = view.findViewById(R.id.tvGeneracionTotal);
        tvConsumoPromedio = view.findViewById(R.id.tvConsumoTotal);
        tvCuotaAutarquica = view.findViewById(R.id.tvCuotaAutarquica);
        pbCuotaAutarquica = view.findViewById(R.id.pbCuotaAutarquica);

        // Configura el clic del TextView para mostrar el BottomSheet
        TextView tvGeneracionConsumo = view.findViewById(R.id.tvGeneracionConsumo);
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
                        // Limitar la lista a los últimos 20 datos (si hay más de 20)
                        int limit = 20;
                        if (historialList.size() > limit) {
                            historialList = historialList.subList(historialList.size() - limit, historialList.size());
                        }

                        ArrayList<Entry> generationEntries = new ArrayList<Entry>();
                        ArrayList<Entry> consumptionEntries = new ArrayList<>();
                        ArrayList<Entry> gridEntries = new ArrayList<>();

                        // Inicializamos las variables para acumular los valores de generación, consumo, y otros
                        double totalGeneracion = 0;
                        double totalConsumoRed = 0;
                        double totalCuotaAutarquica = 0;  // Variable para la suma de cuotas autárquicas

                        // Variables adicionales para los nuevos cálculos
                        double totalConsumoRedDirecto = 0;
                        double totalGeneracionDirecta = 0;
                        double totalAutoconsumo = 0;
                        double totalCuotaConsumoDirecto = 0;
                        double totalCuotaAutoconsumo = 0;
                        double totalInyeccionRed = 0;

                        double totalPrevencionCO2 = 0;
                        double emisionesPorKWh = 0.9;

                        // Recorremos la lista de historial limitada y llenamos las listas de entradas para la gráfica
                        for (int i = 0; i < historialList.size(); i++) {
                            HistorialRendimiento historial = historialList.get(i);

                            // Usamos la posición del array como el eje X (por ejemplo, el índice i)
                            float xValue = i;

                            // Agregar los valores correspondientes a generación, consumo y consumo de la red
                            generationEntries.add(new Entry(xValue, (float) historial.getGeneracion()));
                            consumptionEntries.add(new Entry(xValue, (float) historial.getConsumoTotal()));
                            gridEntries.add(new Entry(xValue, (float) historial.getConsumoRed()));

                            // Acumulamos los valores de generación y consumo de la red
                            totalGeneracion += historial.getGeneracion();
                            totalConsumoRed += historial.getConsumoRed();

                            // Calcular la cuota autárquica para cada registro
                            if (historial.getConsumoTotal() != 0) {
                                double cuotaAutarquica = (historial.getGeneracion() / historial.getConsumoTotal()) * 100;
                                totalCuotaAutarquica += cuotaAutarquica;

                                // Cálculos adicionales
                                // Sumar los consumos de la red
                                totalConsumoRedDirecto += historial.getConsumoRed();

                                // Calcular la cuota de consumo directo con respecto al total de consumo
                                double cuotaConsumoDirecto = (historial.getConsumoRed() / historial.getConsumoTotal()) * 100;
                                totalCuotaConsumoDirecto += cuotaConsumoDirecto;

                                // Sumar las generaciones para calcular el autoconsumo
                                totalGeneracionDirecta += historial.getGeneracion();

                                // Calcular la cuota de autoconsumo respecto al consumo total
                                double cuotaAutoconsumo = (historial.getGeneracion() / historial.getConsumoTotal()) * 100;
                                totalCuotaAutoconsumo += cuotaAutoconsumo;

                                // Calcular la inyección a la red (cuando la generación es mayor que el consumo de la red)
                                if (historial.getGeneracion() > historial.getConsumoTotal()) {
                                    totalInyeccionRed += (historial.getGeneracion() - historial.getConsumoTotal());
                                }

                                double prevencionCO2 = historial.getGeneracion() * emisionesPorKWh;
                                totalPrevencionCO2 += prevencionCO2;
                            }
                        }

                        // Calculamos el promedio de generación, consumo y cuota autárquica
                        double generacionPromedio = totalGeneracion / historialList.size();
                        double consumoRedPromedio = totalConsumoRed / historialList.size();
                        double cuotaAutarquicaPromedio = totalCuotaAutarquica / historialList.size();

                        // Calcular promedios adicionales
                        double promedioConsumoDirecto = totalConsumoRedDirecto / historialList.size();
                        double promedioCuotaConsumoDirecto = totalCuotaConsumoDirecto / historialList.size();
                        double promedioAutoconsumo = totalGeneracionDirecta / historialList.size();
                        double promedioCuotaAutoconsumo = totalCuotaAutoconsumo / historialList.size();
                        double promedioInyeccionRed = totalInyeccionRed / historialList.size();

                        // Mostrar los promedios en los TextViews
                        tvGeneracionPromedio.setText(String.format("%.2f kWh", generacionPromedio));
                        tvConsumoPromedio.setText(String.format("%.2f kWh", consumoRedPromedio));
                        tvCuotaAutarquica.setText(String.format("%.2f%%", cuotaAutarquicaPromedio));

                        // Asignar los nuevos valores a los TextViews correspondientes
                        tvValorConsumoDirecto.setText(String.format("%.2f kWh", promedioConsumoDirecto));
                        tvCuotaValorConsumoDirecto.setText(String.format("%.2f%%", promedioCuotaConsumoDirecto));
                        tvValorAutoconsumo.setText(String.format("%.2f kWh", promedioAutoconsumo));
                        tvCuotaValorAutoconsumo.setText(String.format("%.2f%%", promedioCuotaAutoconsumo));
                        tvValorConsumoRed.setText(String.format("%.2f kWh", totalConsumoRedDirecto));
                        tvInyeccionValorRed.setText(String.format("%.2f kWh", promedioInyeccionRed));

                        // Actualizar la ProgressBar según la cuota autárquica promedio
                        pbCuotaAutarquica.setProgress((int) cuotaAutarquicaPromedio);

                        String prevencionCO2Formatted = String.format("%.2f", totalPrevencionCO2); // en kg
                        double prevencionCO2Toneladas = totalPrevencionCO2 / 1000; // en toneladas

                        // Asignamos los valores a los TextViews
                        tvValorPrevencion.setText(String.format("%s kg", prevencionCO2Formatted));

                        // Pasamos los datos a la configuración de la gráfica
                        ConfiguracionGrafica.configurar(lineChart, generationEntries, consumptionEntries, gridEntries);
                    } else {
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

        //sajiodsajiodhawoidjasiodjsiofjs
    }
}