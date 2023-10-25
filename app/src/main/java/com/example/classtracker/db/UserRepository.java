package com.example.classtracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("lastName", user.getLastName());
        values.put("rut", user.getRut());
        values.put("email", user.getEmail());
        values.put("institution", user.getInstitution());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        return database.insert("users", null, values);
    }

    public User findUserByEmailOrUsernameAndPassword(String emailOrUsername, String password) {
        String query = "SELECT * FROM users WHERE (email = ? OR rut = ?) AND password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{emailOrUsername, emailOrUsername, password});

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
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
}
