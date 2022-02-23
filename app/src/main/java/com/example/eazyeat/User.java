package com.example.eazyeat;

public class User {                                                                                 // Classe 'User' per l'upload sul database e i relativi controlli
    private String nome;
    private String email;
    private String password;

    private boolean flagLogged;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isFlagLogged() { return flagLogged; }

    public void setFlagLogged(boolean flagLogged) { this.flagLogged = flagLogged; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public User() {     // Costruttore di default
        this.nome = "";
        this.email = "";
        this.password = "";
        this.flagLogged = false;
    }

    public User(String nome, String email, String password, boolean flagLogged) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.flagLogged = flagLogged;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
