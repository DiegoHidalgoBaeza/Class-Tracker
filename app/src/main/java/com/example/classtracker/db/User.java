package com.example.classtracker.db;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class User {
    private String name;
    private String lastName;
    private String rut;
    private String email;
    private String institution;
    private String password;
    private String role;
    private byte[] profileImage;

    public User(String name, String lastName, String rut, String email, String institution, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.rut = rut;
        this.email = email;
        this.institution = institution;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRut() {
        return rut;
    }

    public String getEmail() {
        return email;
    }

    public String getInstitution() {
        return institution;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setProfileImage(Bitmap profileImageBitmap) {
        // Convierte el Bitmap en un arreglo de bytes
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profileImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.profileImage = stream.toByteArray();
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
