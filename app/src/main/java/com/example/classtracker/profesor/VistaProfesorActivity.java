package com.example.classtracker.profesor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classtracker.LoginActivity;
import com.example.classtracker.R;

public class VistaProfesorActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView settings;
    private Button button1, button2, button4, button5, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_profesor);

        // Enlaza las vistas con los elementos de la interfaz de usuario
        imageView = findViewById(R.id.imageView);
        settings = findViewById(R.id.settings);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        btnMap = findViewById(R.id.btnMap);


        // Configura un listener para el botón 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón 1
                Toast.makeText(VistaProfesorActivity.this, "Botón 1 pulsado", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura un listener para el botón 2 (Generar Código)
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón "Generar Código"
                // Aquí iniciamos la actividad GenCodeActivity
                Intent intent = new Intent(VistaProfesorActivity.this, GenCodeActivity.class);
                startActivity(intent);
            }
        });

        // Configura un listener para el botón 4 (Cerrar Sesión)
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón "Cerrar Sesión"
                // Aquí iniciamos la actividad LoginActivity
                Intent intent = new Intent(VistaProfesorActivity.this, LoginActivity.class);
                startActivity(intent);

                // Cerramos la actividad actual (opcional)
                finish();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón "Mapa"
                // Aquí iniciamos la actividad MapaActivity
                Intent intent = new Intent(VistaProfesorActivity.this, MapaActivity.class);
                startActivity(intent);

                // Cerramos la actividad actual (opcional)
                finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón "Ayuda"
                // Aquí iniciamos la actividad TutorialActivity
                Intent intent = new Intent(VistaProfesorActivity.this, TutorialActivity.class);
                startActivity(intent);

                // Cerramos la actividad actual (opcional)
                finish();
            }
        });

        // Configura un listener para el icono de "settings"
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un objeto PopupMenu y lo muestra
                PopupMenu popupMenu = new PopupMenu(VistaProfesorActivity.this, settings);
                popupMenu.getMenuInflater().inflate(R.menu.menu_settings, popupMenu.getMenu());

                // Configura un listener para la opción "Editar Perfil"
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_edit_profile) {
                            // Acción a realizar cuando se selecciona "Editar Perfil"
                            Intent intent = new Intent(VistaProfesorActivity.this, EditProfileProfesorActivity.class);
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
