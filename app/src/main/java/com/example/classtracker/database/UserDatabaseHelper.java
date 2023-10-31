package com.example.classtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Classtracker.db";
    private static final int DATABASE_VERSION = 2; // Aumenta la versión de la base de datos

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "lastName TEXT," +
                "rut TEXT," +
                "email TEXT," +
                "institution TEXT," +
                "password TEXT," +
                "role TEXT," +
                "profileimage TEXT)"; // Agrega el campo profileimage
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar lógica para actualizar la base de datos si es necesario
    }
}
