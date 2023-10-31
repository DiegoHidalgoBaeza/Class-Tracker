package com.example.classtracker.profesor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.classtracker.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenCodeActivity extends Activity {

    private TextView codeTextView;
    private ImageView qrCodeImageView;
    private Button volverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gencode);

        codeTextView = findViewById(R.id.codeTextView);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        volverButton = findViewById(R.id.volver);

        // Genera un código alfanumérico de 8 dígitos aleatorio
        String generatedCode = generateRandomCode(8);

        // Muestra el código generado en la TextView
        codeTextView.setText("Código: " + generatedCode);

        // Genera y muestra el código QR
        generateAndShowQRCode(generatedCode);

        // Configura un OnClickListener para el botón "volver"
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad VistaProfesor
                Intent intent = new Intent(GenCodeActivity.this, VistaProfesorActivity.class);
                startActivity(intent);
            }
        });
    }

    private String generateRandomCode(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int randomIndex = (int) (Math.random() * characters.length());

            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }

    private void generateAndShowQRCode(String content) {
        try {
            // Crea un objeto QRCodeWriter
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Codifica el contenido en un formato de matriz de bits (BitMatrix)
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);

            // Convierte la matriz de bits en un objeto Bitmap para mostrar
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            // Muestra el código QR en el ImageView
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
