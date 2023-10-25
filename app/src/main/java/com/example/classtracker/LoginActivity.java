package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.classtracker.db.User;
import com.example.classtracker.db.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmailOrUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailOrUsername = findViewById(R.id.editTextEmailOrUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailOrUsername = editTextEmailOrUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                loginUser(emailOrUsername, password);
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

        // Agrega el TextView "Admin" y su OnClickListener
        TextView adminTextView = findViewById(R.id.textViewAdmin);
        adminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad AdminActivity al hacer clic en "Admin"
                Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(adminIntent);
            }
        });
    }

    private void loginUser(String emailOrUsername, String password) {
        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        User user = userRepository.findUserByEmailOrUsernameAndPassword(emailOrUsername, password);

        if (user != null) {
            // Iniciar la actividad Async para mostrar "Cargando..."
            Intent loadingIntent = new Intent(LoginActivity.this, AsyncActivity.class);
            startActivity(loadingIntent);

            // Esperar un tiempo breve (por ejemplo, 2 segundos) antes de redirigir
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Determinar la actividad de destino según el rol del usuario
                    Intent intent = null;

                    if ("Profesor".equals(user.getRole())) {
                        intent = new Intent(LoginActivity.this, VistaProfesorActivity.class);
                    } else if ("Estudiante".equals(user.getRole())) {
                        intent = new Intent(LoginActivity.this, VistaAlumnoActivity.class);
                    } else {
                        // Manejar otros roles si es necesario
                        // Puedes mostrar un mensaje de error o redirigir a una actividad predeterminada
                        // intent = new Intent(LoginActivity.this, OtraClaseActivity.class);
                    }

                    if (intent != null) {
                        startActivity(intent);
                        finish();  // Cerrar LoginActivity
                    } else {
                        // Manejar el caso donde intent sigue siendo null
                        Toast.makeText(LoginActivity.this, "Rol no válido", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 2000); // Tiempo de espera en milisegundos (2 segundos)
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
