package com.example.classtracker.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classtracker.R;
import com.example.classtracker.database.User;
import com.example.classtracker.database.UserRepository;

public class DeleteUserActivity extends AppCompatActivity {

    private EditText editTextRut, editTextName, editTextLastName; // Agregamos campos de Nombre y Apellido
    private Button buttonSearch, buttonDelete, buttonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteuser);

        editTextRut = findViewById(R.id.editTextSearchRut);
        editTextName = findViewById(R.id.editTextFirstName); // Campo de Nombre
        editTextLastName = findViewById(R.id.editTextLastName); // Campo de Apellido

        // Inicialmente, los campos de Nombre y Apellido estarán ocultos
        editTextName.setVisibility(View.GONE);
        editTextLastName.setVisibility(View.GONE);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonVolver = findViewById(R.id.buttonVolver);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserByRut();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        // Inicializamos el botón "Volver" y le agregamos un OnClickListener
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad AdminActivity cuando se haga clic en el botón "Volver"
                finish(); // Cierra la actividad actual si no deseas volver a ella
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
            // Mostrar los campos de Nombre y Apellido
            editTextName.setVisibility(View.VISIBLE);
            editTextLastName.setVisibility(View.VISIBLE);

            // Llenar los campos de Nombre y Apellido con los datos del usuario
            editTextName.setText(user.getName());
            editTextLastName.setText(user.getLastName());
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }

    private void deleteUser() {
        String rut = editTextRut.getText().toString();

        if (rut.isEmpty()) {
            Toast.makeText(this, "Ingrese un Rut válido", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRepository userRepository = new UserRepository(this);
        userRepository.open();

        int rowsAffected = userRepository.deleteUserByRut(rut);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo eliminar el usuario", Toast.LENGTH_SHORT).show();
        }

        userRepository.close();
    }
}
