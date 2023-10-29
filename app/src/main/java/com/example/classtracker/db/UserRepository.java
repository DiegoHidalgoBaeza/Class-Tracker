package com.example.classtracker.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class UserRepository {
    private SQLiteDatabase database;
    private UserDatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(User user, byte[] profileImage) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("lastName", user.getLastName());
        values.put("rut", user.getRut());
        values.put("email", user.getEmail());
        values.put("institution", user.getInstitution());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());
        values.put("profileimage", profileImage);

        return database.insert("users", null, values);
    }

    public User findUserByEmailOrUsernameAndPassword(String emailOrUsername, String password) {
        String query = "SELECT * FROM users WHERE (email = ? OR rut = ?) AND password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{emailOrUsername, emailOrUsername, password});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") User user = new User(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("lastName")),
                    cursor.getString(cursor.getColumnIndex("rut")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("institution")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("role"))
            );
            cursor.close();
            return user;
        }

        return null;
    }

    @SuppressLint("Range")
    public User findUserByRut(String rut) {
        User user = null;
        String query = "SELECT * FROM users WHERE rut = ?";
        Cursor cursor = database.rawQuery(query, new String[]{rut});

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("lastName")),
                    cursor.getString(cursor.getColumnIndex("rut")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("institution")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("role"))
            );
            cursor.close();
            return user;
        }

        return null;
    }


    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("lastName", user.getLastName());
        values.put("email", user.getEmail());
        values.put("institution", user.getInstitution());
        values.put("password", user.getPassword());

        return database.update("users", values, "rut = ?", new String[]{user.getRut()});
    }


    public int deleteUserByRut(String rut) {
        return database.delete("users", "rut = ?", new String[]{rut});
    }

    @SuppressLint("Range")
    public User findUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            // Recuperar los valores de las columnas en la base de datos
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
            String rut = cursor.getString(cursor.getColumnIndex("rut"));
            String institution = cursor.getString(cursor.getColumnIndex("institution"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String role = cursor.getString(cursor.getColumnIndex("role"));
            byte[] profileImageBytes = cursor.getBlob(cursor.getColumnIndex("profileimage"));

            // Crear una instancia de User con los valores recuperados
            user = new User(name, lastName, rut, email, institution, password, role);

            // Asignar la imagen al usuario si hay datos de imagen en la base de datos
            if (profileImageBytes != null) {
                Bitmap profileImageBitmap = BitmapFactory.decodeByteArray(profileImageBytes, 0, profileImageBytes.length);
                user.setProfileImage(profileImageBitmap);
            }

            cursor.close();
        }

        return user;
    }


    public int updateUserProfileData(String email, String name, String lastName, String institution) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("lastName", lastName);
        values.put("institution", institution);

        return database.update("users", values, "email = ?", new String[]{email});
    }

    public int changeUserPassword(String email, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        return database.update("users", values, "email = ?", new String[]{email});
    }


}
