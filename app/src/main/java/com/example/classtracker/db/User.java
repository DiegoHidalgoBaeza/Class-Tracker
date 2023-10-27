package com.example.classtracker.db;

public class User {
    private String name;
    private String lastName;
    private String rut;
    private String email;
    private String institution;
    private String password;
    private String role;

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

}

