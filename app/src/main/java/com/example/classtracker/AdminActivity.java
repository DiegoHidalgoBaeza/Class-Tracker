package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Aquí puedes agregar la lógica específica para la actividad de administrador

        Button buttonEditUser = findViewById(R.id.buttonEditUser);
        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para iniciar la actividad UpdateUserActivity
                Intent intent = new Intent(AdminActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        Button buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para iniciar la actividad DeleteUserActivity
                Intent intent = new Intent(AdminActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para iniciar la actividad DeleteUserActivity
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}

