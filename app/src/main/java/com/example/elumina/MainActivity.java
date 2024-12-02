package com.example.elumina;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.elumina.Fragment.HistorialFragment;
import com.example.elumina.Fragment.MantenimientoFragment;
import com.example.elumina.Fragment.VistaGeneralFragment;
import com.example.elumina.Materiales.ConfiguracionGrafica;
import com.example.elumina.Materiales.ConfiguracionSwitch;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.materialswitch.MaterialSwitch;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ajustes_222;
    private TextView tabHistorial, tabGeneral, tabMantenimiento;
    private Fragment currentFragment;

    private ImageView perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        perfil = findViewById(R.id.perfil_usuario);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PerfilClienteActivity.class);
                startActivity(intent);
            }
        });


        ImageView gifImageView = findViewById(R.id.perfil_usuario);
        Glide.with(this)
                .load(R.drawable.gif_perfil_111)
                .into(gifImageView);


        ajustes_222 = findViewById(R.id.ajustes_444);

        ajustes_222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });

        // Inicializar tabs
        tabHistorial = findViewById(R.id.tabHistorial);
        tabGeneral = findViewById(R.id.tabGeneral);
        tabMantenimiento = findViewById(R.id.tabPronostico);

        // Establecer fragmento inicial
        if (savedInstanceState == null) {
            currentFragment = new HistorialFragment();
            loadFragment(currentFragment, 0); // Sin animación al iniciar
        }

        // Configurar clics en las pestañas
        tabHistorial.setOnClickListener(v -> {
            if (!(currentFragment instanceof HistorialFragment)) {
                currentFragment = new HistorialFragment();
                loadFragment(currentFragment, -1); // Hacia la izquierda
            }
        });

        tabGeneral.setOnClickListener(v -> {
            if (!(currentFragment instanceof VistaGeneralFragment)) {
                if (currentFragment instanceof HistorialFragment) {
                    loadFragment(new VistaGeneralFragment(), 1); // Hacia la derecha
                } else {
                    loadFragment(new VistaGeneralFragment(), -1); // Hacia la izquierda
                }
                currentFragment = new VistaGeneralFragment();
            }
        });

        tabMantenimiento.setOnClickListener(v -> {
            if (!(currentFragment instanceof MantenimientoFragment)) {
                currentFragment = new MantenimientoFragment();
                loadFragment(currentFragment, 1); // Hacia la derecha
            }
        });
    }

    private void loadFragment(Fragment fragment, int direction) {
        int enterAnim, exitAnim;

        // Determinar la animación basada en la dirección
        if (direction == 1) { // Derecha
            enterAnim = R.anim.slide_in_right;
            exitAnim = R.anim.slide_out_left;
        } else if (direction == -1) { // Izquierda
            enterAnim = R.anim.slide_in_left;
            exitAnim = R.anim.slide_out_right;
        } else { // Sin animación
            enterAnim = 0;
            exitAnim = 0;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // Método para ocultar botones de navegación y barra de estado
    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI(); // Restaurar modo de inmersión al recuperar el foco
        }
    }
}