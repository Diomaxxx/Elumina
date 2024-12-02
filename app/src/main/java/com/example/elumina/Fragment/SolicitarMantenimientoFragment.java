package com.example.elumina.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.elumina.R;

import java.util.Calendar;

public class SolicitarMantenimientoFragment extends Fragment {
    private EditText editTextFecha;

    public SolicitarMantenimientoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        View view = inflater.inflate(R.layout.cliente_solicitar_mantenimiento, container, false);

        // Obtener el EditText para la fecha
        editTextFecha = view.findViewById(R.id.editTextFecha);

        // Configurar el listener para el EditText
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha actual
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        (view1, yearSelected, monthSelected, dayOfMonth) -> {
                            // Mostrar la fecha seleccionada en el EditText
                            String fechaSeleccionada = dayOfMonth + "/" + (monthSelected + 1) + "/" + yearSelected;

                            // Asegúrate de que editTextFecha sea el tipo correcto
                            if (editTextFecha != null) {
                                editTextFecha.setText(fechaSeleccionada);
                            }
                        },
                        year, month, day);

                // Mostrar el dialogo
                datePickerDialog.show();
            }
        });

        return view;
    }
}