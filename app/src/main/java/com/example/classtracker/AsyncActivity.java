package com.example.classtracker;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncActivity extends AppCompatActivity {

    // Declarar variables
    TextView textView;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        // Enlazar las variables con los ID del XML
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageViewLogin);

        // Instanciar el método asincrónico y ejecutarlo
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            // Iniciar la actividad en segundo plano
            // Esperar 5 segundos
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Operación Completa";
        }

        @Override
        protected void onPostExecute(String result) {
            // Actualizar la interfaz con el texto
            textView.setText(result);
            // Mostrar la imagen
            imageView.setVisibility(ImageView.VISIBLE);
        }
    }
}
