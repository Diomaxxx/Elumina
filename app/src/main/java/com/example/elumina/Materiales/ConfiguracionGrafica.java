package com.example.elumina.Materiales;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ConfiguracionGrafica {


    public static void configurar(LineChart lineChart) {
        // Crear datos de ejemplo
        ArrayList<Entry> generationEntries = new ArrayList<>();
        ArrayList<Entry> consumptionEntries = new ArrayList<>();
        ArrayList<Entry> gridEntries = new ArrayList<>();

        for (int i = 0; i <= 24; i++) {
            generationEntries.add(new Entry(i, (float) (Math.random() * 15 + 5))); // Generación
            consumptionEntries.add(new Entry(i, (float) (Math.random() * 10 + 20))); // Consumo
            gridEntries.add(new Entry(i, (float) (Math.random() * 5 + 10))); // Consumo de la red
        }

        // Configurar conjuntos de datos
        LineDataSet generationDataSet = new LineDataSet(generationEntries, "Generación");
        generationDataSet.setColor(Color.GREEN);
        generationDataSet.setFillColor(Color.GREEN);
        generationDataSet.setDrawFilled(true);
        generationDataSet.setLineWidth(2f);

        LineDataSet consumptionDataSet = new LineDataSet(consumptionEntries, "Consumo");
        consumptionDataSet.setColor(Color.GRAY);
        consumptionDataSet.setFillColor(Color.LTGRAY);
        consumptionDataSet.setDrawFilled(true);
        consumptionDataSet.setLineWidth(2f);

        LineDataSet gridDataSet = new LineDataSet(gridEntries, "Consumo de la red");
        gridDataSet.setColor(Color.RED);
        gridDataSet.setFillColor(Color.RED);
        gridDataSet.setDrawFilled(true);
        gridDataSet.setLineWidth(2f);

        // Agregar conjuntos de datos a la gráfica
        LineData lineData = new LineData(generationDataSet, consumptionDataSet, gridDataSet);
        lineChart.setData(lineData);

        // Configuración adicional de la gráfica
        lineChart.getDescription().setText("Generación y Consumo");
        lineChart.getDescription().setTextSize(12f);
        lineChart.animateX(1500);

        // Configuración de la leyenda
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.CIRCLE);

        // Actualizar la gráfica
        lineChart.invalidate();
    }
}
