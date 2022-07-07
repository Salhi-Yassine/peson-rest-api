package com.example.miniprojet.service;

import com.example.miniprojet.entity.Personne;

import java.util.List;

public interface PersonneService {

    List<Personne> findAll();
    Personne create(Personne newPersonne);
    Personne findById(Long id);
    Personne update(Personne newPersonne, Long id);
    void delete(Long id);
}
