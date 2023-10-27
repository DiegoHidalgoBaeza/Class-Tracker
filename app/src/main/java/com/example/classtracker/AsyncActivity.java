package com.example.classtracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class AsyncActivity extends AppCompatActivity {

    // Declarar variables
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        // Enlazar las variables con los ID del XML
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageViewLogin);

        // Iniciar la animación GIF
        loadGifAnimation();

        // Instanciar el método asincrónico y ejecutarlo
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    private void loadGifAnimation() {
        // Cargar la animación GIF utilizando Glide
        Glide.with(this)
                .load(R.raw.loading_animation)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // Evitar almacenar en caché
                        .skipMemoryCache(true)) // Evitar almacenar en memoria caché
                .into(imageView);
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
            // La imagen ya está cargada en la animación GIF
        }
    }
}
