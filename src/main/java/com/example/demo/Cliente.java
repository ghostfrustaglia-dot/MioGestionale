package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Dice a Spring di creare una tabella nel database per questa classe
public class Cliente {

    @Id // Specifica che questo campo è la chiave primaria (ID unico)
    @GeneratedValue(strategy = GenerationType.AUTO) // L'ID viene generato automaticamente (1, 2, 3...)
    private Long id;

    private String nome;
    private String email;
    private String telefono;

    // 1. COSTRUTTORE VUOTO (Obbligatorio per Spring Boot/JPA)
    public Cliente() {
    }

    // 2. COSTRUTTORE COMPLETO (Utile per creare velocemente nuovi clienti)
    public Cliente(String nome, String email, String telefono) {
        this.nome = nome;
        this.email = email;
        this.telefono = telefono;
    }

    // 3. GETTER E SETTER (Fondamentali per evitare l'errore 500/Whitelabel)
    // Senza questi, Thymeleaf non può leggere c.id o c.nome

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}