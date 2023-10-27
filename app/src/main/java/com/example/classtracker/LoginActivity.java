package com.example.classtracker;

import android.content.Intent;
import android.content.SharedPreferences;
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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView adminTextView = findViewById(R.id.textViewAdmin);
        adminTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("userEmail", emailOrUsername);
            editor.apply();

            Intent loadingIntent = new Intent(LoginActivity.this, AsyncActivity.class);
            startActivity(loadingIntent);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = null;

                    if ("Profesor".equals(user.getRole())) {
                        intent = new Intent(LoginActivity.this, VistaProfesorActivity.class);
                    } else if ("Estudiante".equals(user.getRole())) {
                        intent = new Intent(LoginActivity.this, VistaAlumnoActivity.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "Rol no válido", Toast.LENGTH_SHORT).show();
                    }

                    if (intent != null) {
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Rol no válido", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 2000);
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
