package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                loginUser(username, password);
            }
        });

        TextView recuperarCuentaTextView = findViewById(R.id.recuperar_cuenta);
        recuperarCuentaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad RecoveryActivity al hacer clic en "Recuperar Cuenta"
                Intent intent = new Intent(LoginActivity.this, RecoveryActivity.class);
                startActivity(intent);
            }
        });

        TextView registrarTextView = findViewById(R.id.registrar);
        registrarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad RegisterActivity al hacer clic en "Registrar"
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String username, String password) {
        Intent intent = null;

        if (username.equals("profesor") && password.equals("profesor")) {
            // Credenciales de administrador, redirige a VistaProfesor
            intent = new Intent(LoginActivity.this, VistaProfesorActivity.class);
        } else if (username.equals("alumno") && password.equals("alumno")) {
            // Credenciales de alumno, redirige a VistaAlumno
            intent = new Intent(LoginActivity.this, VistaAlumnoActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish(); // Cierra la actividad de inicio de sesión para que el usuario no pueda volver atrás con el botón Atrás
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}
