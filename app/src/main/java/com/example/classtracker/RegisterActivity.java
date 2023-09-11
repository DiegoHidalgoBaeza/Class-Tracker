package com.example.classtracker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextID, editTextMail, editTextInstitution;
    private Spinner spinnerRole; // Nuevo Spinner
    private Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.Register_Name);
        editTextLastName = findViewById(R.id.Register_LastName);
        editTextID = findViewById(R.id.Register_ID);
        editTextMail = findViewById(R.id.Register_Mail);
        editTextInstitution = findViewById(R.id.Instiucion);
        spinnerRole = findViewById(R.id.spinnerRole); // Asociar con el Spinner en el XML
        buttonRegistrar = findViewById(R.id.Register_Registrar);

        // Configurar opciones del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Manejar la selección del Spinner
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedRole = parentView.getItemAtPosition(position).toString();
                // Puedes usar la variable 'selectedRole' para saber si es "Profesor" o "Estudiante"
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No se ha seleccionado nada
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes obtener el valor seleccionado del Spinner y otros datos del usuario
                String name = editTextName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String id = editTextID.getText().toString();
                String mail = editTextMail.getText().toString();
                String institution = editTextInstitution.getText().toString();
                String selectedRole = spinnerRole.getSelectedItem().toString();

                // Luego, puedes registrar al usuario según los datos recopilados
                // ...
            }
        });

        // Resto del código de configuración y manejo de botones aquí...
    }
}
