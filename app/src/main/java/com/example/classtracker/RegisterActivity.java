package com.example.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.example.classtracker.db.User;
import com.example.classtracker.db.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextID, editTextMail, editTextInstitution, editTextPassword, editTextConfirmPassword;
    private Spinner spinnerRole;
    private Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button backButton = findViewById(R.id.buttonback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextName = findViewById(R.id.Register_Name);
        editTextLastName = findViewById(R.id.Register_LastName);
        editTextID = findViewById(R.id.Register_ID);
        editTextMail = findViewById(R.id.Register_Mail);
        editTextInstitution = findViewById(R.id.Instiucion);
        editTextPassword = findViewById(R.id.Register_Password);
        editTextConfirmPassword = findViewById(R.id.Register_ConfirmPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        buttonRegistrar = findViewById(R.id.Register_Registrar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String id = editTextID.getText().toString();
                String mail = editTextMail.getText().toString();
                String institution = editTextInstitution.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String selectedRole = spinnerRole.getSelectedItem().toString();

                if (name.isEmpty() || lastName.isEmpty() || id.isEmpty() || mail.isEmpty() || institution.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole.equals("Seleccionar")) {
                    // Mostrar un mensaje si algún campo está vacío
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    // Mostrar un mensaje si las contraseñas no coinciden
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else if (!isValidRut(id)) {
                    // Validar si el RUT no es válido
                    Toast.makeText(RegisterActivity.this, "El RUT no es válido", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(mail)) {
                    // Validar si el correo no es válido
                    Toast.makeText(RegisterActivity.this, "El correo no es válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Contraseñas coinciden y el RUT es válido, continuar con la inserción en la base de datos
                    User user = new User(name, lastName, id, mail, institution, password, selectedRole);
                    UserRepository userRepository = new UserRepository(RegisterActivity.this);
                    userRepository.open();
                    long result = userRepository.insertUser(user);
                    userRepository.close();

                    if (result != -1) {
                        // Inserción exitosa, mostrar mensaje de éxito
                        Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                        // Redirigir al usuario a la actividad de inicio de sesión
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Inserción fallida, mostrar mensaje de error
                        Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Método para validar un RUT chileno
    private boolean isValidRut(String rut) {
        // Expresión regular para validar un RUT chileno
        String regex = "\\d{1,8}-[kK0-9]";

        // Validar el RUT usando la expresión regular
        if (!rut.matches(regex)) {
            return false;
        }

        // Separar el número y el dígito verificador
        String[] parts = rut.split("-");
        String rutNumber = parts[0];
        String dv = parts[1];

        // Verificar el dígito verificador
        try {
            int rutInt = Integer.parseInt(rutNumber);
            char dvChar = dv.charAt(0);
            return dvChar == calculateDV(rutInt);
        } catch (Exception e) {
            return false;
        }
    }

    // Método para calcular el dígito verificador
    private char calculateDV(int rut) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return (char) (s != 0 ? s + 47 : 75);
    }

    // Método para validar un correo electrónico
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // Expresión regular para validar correo electrónico
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
