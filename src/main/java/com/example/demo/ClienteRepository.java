package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Spring Boot creerà automaticamente la query SQL per noi!
    // Cerca tutti i clienti il cui nome contiene la parola cercata
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}