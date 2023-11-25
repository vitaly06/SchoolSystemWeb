package ru.okei.urbaton.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class Person {
    @NotEmpty(message = "1")
    @Size(min=3, max=45, message = "Длина не более 45 символов")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "1")
    private String email;
    @NotEmpty(message = "1")
    private String password;
    @NotEmpty(message = "Поле не может быть пустым")
    private String confrimPassword;
    @NotEmpty(message = "Поле не может быть пустым")
    private String naprav;
    private String typeProfile;

    public Person(){

    }

    public Person(String name, String email, String password, String confrimPassword, String naprav, String typeProfile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confrimPassword = confrimPassword;
        this.naprav = naprav;
        this.typeProfile = typeProfile;
    }

    public String getConfrimPassword() {
        return confrimPassword;
    }

    public void setConfrimPassword(String confrimPassword) {
        this.confrimPassword = confrimPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNaprav() {
        return naprav;
    }

    public void setNaprav(String naprav) {
        this.naprav = naprav;
    }

    public String getTypeProfile() {
        return typeProfile;
    }

    public void setTypeProfile(String typeProfile) {
        this.typeProfile = typeProfile;
    }
}
