package com.example.classtracker;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videotutorial); // Establece el contenido de la actividad con el layout "activity_video.xml"

        // Obtiene una referencia al VideoView desde el layout
        VideoView mivideo = findViewById(R.id.tuto);

        // Crea la URI del video desde la ruta del recurso "R.raw.juego"
        String video= "android.resource://" + getPackageName() + "/" + R.raw.tutoph;
        Uri uri = Uri.parse(video);

        // Establece la URI del video en el VideoView
        mivideo.setVideoURI(uri);

        // Crea un objeto MediaController para controlar la reproducción del video
        MediaController mediaController = new MediaController(this);

        // Asocia el MediaController con el VideoView
        mivideo.setMediaController(mediaController);

        // Establece la vista a la que se ancla el MediaController (en este caso, el VideoView)
        mediaController.setAnchorView(mivideo);

        // Aplica una animación de escala al VideoView para que aparezca gradualmente
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000); // Duración de la animación en milisegundos
        mivideo.startAnimation(animation);

        // Aplica una animación de desplazamiento al VideoView
        // Animation animation = new TranslateAnimation(0, 0, 100, 0);
        // animation.setDuration(1000); // Duración de la animación en milisegundos
        //
        // mivideo.startAnimation(animation);

        // Establece un listener para el evento de preparación del MediaPlayer
        mivideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mivideo.start(); // Inicia la reproducción del video cuando está preparado
            }
        });
    }
}
