package com.example.classtracker.profesor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classtracker.R;
import com.example.classtracker.database.User;
import com.example.classtracker.database.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileProfesorActivity extends AppCompatActivity {
    private EditText editTextName, editTextLastName, editTextEmail, editTextInstitution, editTextPassword,
            editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private Button buttonSaveData, buttonChangePassword, buttonChangeImage;
    private ImageView profileImageView;
    private static final int PICK_IMAGE_REQUEST = 1; // Request code for image selection
    private static final int MAX_IMAGE_SIZE = 5 * 1024 * 1024; // Tamaño máximo de imagen (5 MB)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextInstitution = findViewById(R.id.editTextInstitution);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSaveData = findViewById(R.id.buttonSaveData);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        profileImageView = findViewById(R.id.profileImageView); // ImageView para mostrar la imagen de perfil

        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", "");

        editTextEmail.setText(userEmail);

        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        User user = userRepository.findUserByEmail(userEmail);

        if (user != null) {
            editTextName.setText(user.getName());
            editTextLastName.setText(user.getLastName());
            editTextInstitution.setText(user.getInstitution());
            editTextPassword.setText(user.getPassword()); // Establecer la contraseña actual

            // Mostrar la imagen de perfil si está disponible
            if (user.getProfileImage() != null) {
                byte[] imageBytes = user.getProfileImage();
                Bitmap profileBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileImageView.setImageBitmap(profileBitmap);
            }
        } else {
            Toast.makeText(this, "Usuario no encontrado en la base de datos", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();

        buttonChangeImage = findViewById(R.id.buttonChangeImage);

        buttonChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelection();
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a VistaProfesorActivity
                Intent intent = new Intent(EditProfileProfesorActivity.this, VistaProfesorActivity.class);

                // Iniciar la actividad VistaProfesorActivity
                startActivity(intent);
            }
        });

        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfileData();
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserPassword();
            }
        });
    }

    // Add a method to open the image selection dialog
    private void openImageSelection() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);

                // Verificar el tamaño de la imagen antes de cargarla
                int imageSize = imageStream.available();
                if (imageSize > MAX_IMAGE_SIZE) {
                    Toast.makeText(this, "La imagen es demasiado grande", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                // Set the selected image in the ImageView
                profileImageView.setImageBitmap(selectedImage);

                // Convert the Bitmap to a byte array and update the profile image in the database
                byte[] profileImageBytes = convertBitmapToBytes(selectedImage);
                updateProfileImage(profileImageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Add a method to convert a Bitmap to a byte array
    @NonNull
    private byte[] convertBitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // Add a method to update the profile image in the database
    private void updateProfileImage(byte[] profileImage) {
        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", "");

        // Comprimir la imagen antes de guardarla en la base de datos
        Bitmap profileBitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
        Bitmap compressedBitmap = compressBitmap(profileBitmap, 1024); // Puedes ajustar el tamaño máximo en bytes

        // Convertir el Bitmap comprimido a un byte array
        byte[] compressedProfileImage = convertBitmapToBytes(compressedBitmap);

        int rowsAffected = userRepository.updateProfileImage(userEmail, compressedProfileImage);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar la imagen de perfil", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }

    // Método para comprimir un Bitmap a un tamaño máximo en bytes
    private Bitmap compressBitmap(@NonNull Bitmap bitmap, int maxSizeInBytes) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100;

        do {
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            quality -= 10;
        } while (stream.size() > maxSizeInBytes && quality >= 0);

        return BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
    }

    private void updateUserProfileData() {
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String institution = editTextInstitution.getText().toString();

        if (email.isEmpty() || name.isEmpty() || lastName.isEmpty() || institution.isEmpty()) {
            Toast.makeText(EditProfileProfesorActivity.this, "Complete todos los campos de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(EditProfileProfesorActivity.this);
        userRepository.open();

        int rowsAffected = userRepository.updateUserProfileData(email, name, lastName, institution);

        if (rowsAffected > 0) {
            Toast.makeText(EditProfileProfesorActivity.this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfileProfesorActivity.this, "No se pudo actualizar los datos del usuario", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }

    private void changeUserPassword() {
        String email = editTextEmail.getText().toString();
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (email.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(EditProfileProfesorActivity.this, "Complete todos los campos de contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!currentPassword.equals(editTextPassword.getText().toString())) {
            Toast.makeText(EditProfileProfesorActivity.this, "La contraseña actual no coincide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 8) {
            Toast.makeText(EditProfileProfesorActivity.this, "La nueva contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(EditProfileProfesorActivity.this, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(EditProfileProfesorActivity.this);
        userRepository.open();

        int rowsAffected = userRepository.changeUserPassword(email, newPassword);

        if (rowsAffected > 0) {
            Toast.makeText(EditProfileProfesorActivity.this, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfileProfesorActivity.this, "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
