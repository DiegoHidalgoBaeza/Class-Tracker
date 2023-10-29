package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class VistaAlumnoActivity extends AppCompatActivity {

    private ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_alumno);

        ImageView imageView = findViewById(R.id.imageViewAlumno);
        settings = findViewById(R.id.settings);
        Button button1 = findViewById(R.id.button1Alumno);
        Button button2 = findViewById(R.id.button2Alumno);
        Button button5 = findViewById(R.id.button5Alumno); // Botón "Cerrar Sesión"

        // Configura un evento de clic para el botón 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se hace clic en el botón 1
                Toast.makeText(VistaAlumnoActivity.this, "Botón 1 pulsado", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura un evento de clic para el botón 2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se hace clic en el botón 2
                Toast.makeText(VistaAlumnoActivity.this, "Botón 2 pulsado", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura un evento de clic para el botón "Cerrar Sesión"
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se hace clic en el botón "Cerrar Sesión"
                // Iniciar LoginActivity
                Intent intent = new Intent(VistaAlumnoActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finalizar la actividad actual (VistaAlumno)
                finish();
            }
        });

        // Configura un listener para el icono de "settings"
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un objeto PopupMenu y lo muestra
                PopupMenu popupMenu = new PopupMenu(VistaAlumnoActivity.this, settings);
                popupMenu.getMenuInflater().inflate(R.menu.menu_settings, popupMenu.getMenu());

                // Configura un listener para la opción "Editar Perfil"
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_edit_profile) {
                            // Acción a realizar cuando se selecciona "Editar Perfil"
                            Intent intent = new Intent(VistaAlumnoActivity.this, EditProfileProfesorActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }
}