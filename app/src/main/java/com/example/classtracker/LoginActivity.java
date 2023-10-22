package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
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
    }

    private void loginUser(String emailOrUsername, String password) {
        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        User user = userRepository.findUserByEmailOrUsernameAndPassword(emailOrUsername, password);

        Intent intent = null; // Declarar intent inicializado como null

        if (user != null) {
            String userRole = user.getRole();

            if ("Profesor".equals(userRole)) {
                intent = new Intent(LoginActivity.this, VistaProfesorActivity.class);
            } else if ("Estudiante".equals(userRole)) {
                intent = new Intent(LoginActivity.this, VistaAlumnoActivity.class);
            } else {
                // Manejar otros roles si es necesario
                // Puedes mostrar un mensaje de error o redirigir a una actividad predeterminada
                // intent = new Intent(LoginActivity.this, OtraClaseActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish();
            } else {
                // Manejar el caso donde intent sigue siendo null
                Toast.makeText(LoginActivity.this, "Rol no válido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
