package com.example.elumina;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elumina.Graphql.Repositories.ClienteRepository;
import com.example.elumina.Graphql.Repositories.UsuarioRepository;
import com.example.elumina.Graphql.Service.GraphQLCallback;
import com.example.elumina.Operador.OperardoMain;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button login;
    private EditText correoEditText, passwordEditText;
    private ClienteRepository clienteRepository;
    private UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inicializa los repositorios
        clienteRepository = new ClienteRepository();
        usuarioRepository = new UsuarioRepository();

        correoEditText = findViewById(R.id.editemail);
        passwordEditText = findViewById(R.id.editpassword);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = correoEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validar los campos
                if (correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "Iniciando login con correo: " + correo);

                // Llamar a la API para hacer login como usuario
                usuarioRepository.loginUsuario(correo, password, new GraphQLCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, "Login de usuario exitoso: " + result);
                        try {
                            // Suponiendo que la respuesta contiene un token
                            String token = new JSONObject(result).getJSONObject("data").getString("loginUsuario");

                            // Verificar si el token corresponde a un operador
                            saveTokenAndRedirectToOperador(token);


                        } catch (Exception e) {
                            Log.e(TAG, "Error procesando la respuesta de login de usuario", e);
                            Toast.makeText(LoginActivity.this, "Error en la respuesta de login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        loginConCliente(correo, password);
                    }
                });
            }
        });
    }

    private void loginConCliente(String correo, String password) {
        Log.d(TAG, "Intentando login como cliente con correo: " + correo);

        clienteRepository.loginCliente(correo, password, new GraphQLCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "Login de cliente exitoso: " + result);
                try {
                    // Obtener el token del login de cliente
                    String token = new JSONObject(result).getJSONObject("data").getString("loginCliente");

                    if (token != null) {
                        saveTokenAndRedirectToCliente(token,correo);
                    } else {
                        Log.e(TAG, "No se recibió token de cliente");
                        Toast.makeText(LoginActivity.this, "No se pudo autenticar al cliente", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error procesando la respuesta de login de cliente", e);
                    Toast.makeText(LoginActivity.this, "Error en la respuesta de login de cliente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error durante login de cliente: " + errorMessage);
                Toast.makeText(LoginActivity.this, "No hay registros con ese correo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveTokenAndRedirectToOperador(String token) {
        Log.d(TAG, "Guardando token y redirigiendo al operador");
        Log.d("Token",token);

        // Guardar el token en SharedPreferences
        getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit()
                .putString("token", token)
                .apply();

        Intent intent = new Intent(LoginActivity.this, OperardoMain.class);
        startActivity(intent);
        finish();
    }

    private void saveTokenAndRedirectToCliente(String token, String correo) {
        Log.d(TAG, "Guardando token y redirigiendo al cliente");
        Log.d("Token",token);

        // Guardar el token en SharedPreferences
        getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit()
                .putString("token", token)
                .apply();

        getSharedPreferences("user_prefs", MODE_PRIVATE)
                .edit()
                .putString("clienteEmail", correo)
                .apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Asegúrate de tener la actividad ClienteMain
        startActivity(intent);
        finish();
    }
}
