package com.example.elumina.Operador;

import android.content.Intent;
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

import com.example.elumina.AjustesActivity;
import com.example.elumina.Fragment.HistorialFragment;
import com.example.elumina.Fragment.MantenimientoFragment;
import com.example.elumina.Fragment.SolicitarMantenimientoFragment;
import com.example.elumina.Fragment.VistaGeneralFragment;
import com.example.elumina.FragmentOperador.PrincipalFragment;
import com.example.elumina.MainActivity;
import com.example.elumina.PerfilClienteActivity;
import com.example.elumina.R;

public class OperardoMain extends AppCompatActivity {



    private TextView tabprincipal, tanotificacion, tabmantenimientooperador;
    private Fragment currentFragmentoperador;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.operador_main);





        tabprincipal = findViewById(R.id.tabprincipal1);
//        tabGeneral = findViewById(R.id.tabGeneral);
//        tabMantenimiento = findViewById(R.id.tabPronostico);

        // Establecer fragmento inicial
        if (savedInstanceState == null) {
            currentFragmentoperador = new PrincipalFragment();
            loadFragment(currentFragmentoperador, 0); // Sin animación al iniciar
        }

        // Configurar clics en las pestañas
        tabprincipal.setOnClickListener(v -> {
            if (!(currentFragmentoperador instanceof PrincipalFragment)) {
                currentFragmentoperador = new PrincipalFragment();
                loadFragment(currentFragmentoperador, -1); // Hacia la izquierda
            }
        });

//        tabGeneral.setOnClickListener(v -> {
//            if (!(currentFragmentoperador instanceof VistaGeneralFragment)) {
//                if (currentFragmentoperador instanceof HistorialFragment) {
//                    loadFragment(new VistaGeneralFragment(), 1); // Hacia la derecha
//                } else {
//                    loadFragment(new VistaGeneralFragment(), -1); // Hacia la izquierda
//                }
//                currentFragmentoperador = new VistaGeneralFragment();
//            }
//        });
//
//        tabMantenimiento.setOnClickListener(v -> {
//            if (!(currentFragmentoperador instanceof MantenimientoFragment)) {
//                currentFragmentoperador = new MantenimientoFragment();
//                loadFragment(currentFragmentoperador, 1); // Hacia la derecha
//            }
//        });
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
                .replace(R.id.fragmentOperador, fragment)
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