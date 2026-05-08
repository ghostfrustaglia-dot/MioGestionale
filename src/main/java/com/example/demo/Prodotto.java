package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private int quantita;
    private double prezzo;

    public Prodotto() {}

    public Prodotto(String nome, int quantita, double prezzo) {
        this.nome = nome;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
}