package com.example.elumina.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import com.example.elumina.Materiales.ConfiguracionGrafica;
import com.example.elumina.Materiales.ConfiguracionSwitch;
import com.example.elumina.R;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.materialswitch.MaterialSwitch;

public class HistorialFragment extends Fragment {
    private LineChart lineChart;


    public HistorialFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        // Configurar los gráficos
        lineChart = view.findViewById(R.id.lineChart);
        ConfiguracionGrafica.configurar(lineChart);
        TextView tvGeneracionConsumo = view.findViewById(R.id.tvGeneracionConsumo);

        // Configura el clic del TextView para mostrar el BottomSheet
        tvGeneracionConsumo.setOnClickListener(v -> {
            // Llama a la función para mostrar el BottomSheet
            showBottomSheet();
        });
        // Configurar los switches
       // MaterialSwitch switchVerde = view.findViewById(R.id.materialSwitch1);
       // ConfiguracionSwitch.configurarSwitchVerde(switchVerde);

        //MaterialSwitch switchRojo = view.findViewById(R.id.materialSwitch2);
        //ConfiguracionSwitch.configurarSwitchRojo(switchRojo);

       // MaterialSwitch switchPlomoOscuro = view.findViewById(R.id.materialSwitch3);
      //  ConfiguracionSwitch.configurarSwitchPlomoOscuro(switchPlomoOscuro);

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