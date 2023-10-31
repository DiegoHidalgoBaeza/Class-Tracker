package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.classtracker.database.UserRepository;

public class RecoveryPassActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonRecover;

    private Button buttonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoverypass);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonRecover = findViewById(R.id.buttonRecover);
        buttonVolver = findViewById(R.id.buttonVolver);





        // Agregar esto en el método onCreate de RecoveryPassActivity
        Button buttonRecover = findViewById(R.id.buttonRecover);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iniciamos la actividad LoginActivity
                Intent intent = new Intent(RecoveryPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        buttonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el correo electrónico ingresado por el usuario
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                String userEmail = editTextEmail.getText().toString();

                // Verificar si el usuario existe en la base de datos
                UserRepository userRepository = new UserRepository(RecoveryPassActivity.this);
                userRepository.open();
                boolean userExists = userRepository.doesUserExistByEmail(userEmail);

                if (userExists) {
                    // Generar una contraseña aleatoria de 10 caracteres
                    String newPassword = generateRandomPassword(10);

                    // Actualizar la contraseña del usuario en la base de datos
                    int rowsAffected = userRepository.changeUserPassword(userEmail, newPassword);

                    if (rowsAffected > 0) {
                        // Envía la nueva contraseña por correo electrónico
                        EmailSender.sendEmail(userEmail, newPassword);

                        Toast.makeText(RecoveryPassActivity.this, "Contraseña cambiada con éxito y enviada por correo electrónico", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RecoveryPassActivity.this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                    }
                }

                userRepository.close();
            }
        });

    }
        @NonNull
        private String generateRandomPassword(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder password = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int index = (int) (Math.random() * characters.length());
                password.append(characters.charAt(index));
            }

            return password.toString();
        }

    }

