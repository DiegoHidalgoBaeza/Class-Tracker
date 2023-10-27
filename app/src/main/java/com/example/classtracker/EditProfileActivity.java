package com.example.classtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.classtracker.db.User;
import com.example.classtracker.db.UserRepository;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextEmail, editTextInstitution, editTextPassword,
            editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private Button buttonSaveData, buttonChangePassword;

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
        } else {
            Toast.makeText(this, "Usuario no encontrado en la base de datos", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a VistaProfesorActivity
                Intent intent = new Intent(EditProfileActivity.this, VistaProfesorActivity.class);

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

    private void updateUserProfileData() {
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String institution = editTextInstitution.getText().toString();

        if (email.isEmpty() || name.isEmpty() || lastName.isEmpty() || institution.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Complete todos los campos de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(EditProfileActivity.this);
        userRepository.open();

        int rowsAffected = userRepository.updateUserProfileData(email, name, lastName, institution);

        if (rowsAffected > 0) {
            Toast.makeText(EditProfileActivity.this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfileActivity.this, "No se pudo actualizar los datos del usuario", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }

    private void changeUserPassword() {
        String email = editTextEmail.getText().toString();
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (email.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Complete todos los campos de contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!currentPassword.equals(editTextPassword.getText().toString())) {
            Toast.makeText(EditProfileActivity.this, "La contraseña actual no coincide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 8) {
            Toast.makeText(EditProfileActivity.this, "La nueva contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(EditProfileActivity.this, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(EditProfileActivity.this);
        userRepository.open();

        int rowsAffected = userRepository.changeUserPassword(email, newPassword);

        if (rowsAffected > 0) {
            Toast.makeText(EditProfileActivity.this, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfileActivity.this, "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
