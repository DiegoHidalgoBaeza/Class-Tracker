package com.example.classtracker.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classtracker.R;
import com.example.classtracker.database.User;
import com.example.classtracker.database.UserRepository;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editTextRut, editTextName, editTextLastName, editTextEmail, editTextInstitution, editTextPassword;
    private Button buttonSearch, buttonUpdate, buttonVolver; // Agregamos el botón para "Volver"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        editTextRut = findViewById(R.id.editTextSearchRut);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextInstitution = findViewById(R.id.editTextInstitution);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Inicializamos el botón "Volver" y le agregamos un OnClickListener
        buttonVolver = findViewById(R.id.button5Alumno);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad AdminActivity cuando se haga clic en el botón "Volver"
                Intent adminIntent = new Intent(UpdateUserActivity.this, AdminActivity.class);
                startActivity(adminIntent);
                finish(); // Opcional, cierra la actividad actual si no deseas volver a ella
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserByRut();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void searchUserByRut() {
        String rut = editTextRut.getText().toString();

        if (rut.isEmpty()) {
            Toast.makeText(this, "Ingrese un Rut válido", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        User user = userRepository.findUserByRut(rut);

        if (user != null) {
            // Llenar los campos con los datos del usuario encontrado
            editTextName.setText(user.getName());
            editTextLastName.setText(user.getLastName());
            editTextEmail.setText(user.getEmail());
            editTextInstitution.setText(user.getInstitution());
            editTextPassword.setText(user.getPassword());
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }

    private void updateUser() {
        String rut = editTextRut.getText().toString();
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String institution = editTextInstitution.getText().toString();
        String password = editTextPassword.getText().toString();

        if (rut.isEmpty() || name.isEmpty() || lastName.isEmpty() || email.isEmpty() || institution.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        User user = new User(name, lastName, rut, email, institution, password, "Usuario");
        int rowsAffected = userRepository.updateUser(user);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar el usuario", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
