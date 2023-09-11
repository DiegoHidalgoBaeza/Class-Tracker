package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class VistaProfesorActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_profesor);

        // Enlaza las vistas con los elementos de la interfaz de usuario
        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

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

        // Configura un listener para el botón 3
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar cuando se haga clic en el botón 3
                Toast.makeText(VistaProfesorActivity.this, "Botón 3 pulsado", Toast.LENGTH_SHORT).show();
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
    }
}
