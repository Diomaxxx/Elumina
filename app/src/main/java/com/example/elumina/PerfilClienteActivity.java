package com.example.elumina;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elumina.Graphql.Models.Entity.Cliente;
import com.example.elumina.Graphql.Repositories.ClienteRepository;
import com.example.elumina.Graphql.Service.GraphQLCallback;

public class PerfilClienteActivity extends AppCompatActivity {

    private ClienteRepository clienteRepository;

    private TextView name, lastname, address, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cliente_perfil);

        name = findViewById(R.id.textViewNameValue);
        lastname = findViewById(R.id.textViewLastNameValue);
        address = findViewById(R.id.textViewAddressValue);
        email = findViewById(R.id.textViewEmailValue);
        phone = findViewById(R.id.textViewPhoneValue);


        clienteRepository = new ClienteRepository();
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null) {
            getData(token);
        } else {
            Log.e("PerfilCliente", "Token no encontrado en SharedPreferences");
        }
    }

    private void getData(String token) {
        clienteRepository.obtenerClienteDesdeToken(token, new GraphQLCallback<Cliente>() {
            @Override
            public void onSuccess(Cliente cliente) {
                Log.d("Cliente", "Cliente recibido: " + cliente.getId());

                name.setText(cliente.getNombre());
                lastname.setText(cliente.getApellido());
                email.setText(cliente.getCorreo());
                phone.setText(cliente.getTelefono());
                address.setText(cliente.getDireccion());

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ErrorCliente", "Error al obtener cliente: " + errorMessage);
            }
        });
    }

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
