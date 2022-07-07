package com.example.miniprojet.service.imp;

import com.example.miniprojet.entity.Personne;
import com.example.miniprojet.exception.PersonneNotFoundException;
import com.example.miniprojet.repository.PersonneRepository;
import com.example.miniprojet.service.PersonneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneServiceImp implements PersonneService {

    private final PersonneRepository repository;

    public PersonneServiceImp(PersonneRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Personne> findAll() {
        return repository.findAll();
    }

    @Override
    public Personne create(Personne newPersonne) {
        return  repository.existsByCin(newPersonne.getCin())? null : repository.save(newPersonne);
    }

    @Override
    public Personne findById(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new PersonneNotFoundException(id));
    }

    @Override
    public Personne update(Personne newPersonne, Long id) {
        if(repository.existsByCin(newPersonne.getCin())){ return null;}
         return repository.findById(id) //
                .map(personne -> {
                    personne.setNom(newPersonne.getNom());
                    personne.setPrenom(newPersonne.getPrenom());
                    personne.setCin(newPersonne.getCin());
                    return repository.save(personne);
                }) //
                .orElseThrow(() -> new PersonneNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
         repository.findById(id) //
                .orElseThrow(() -> new PersonneNotFoundException(id));

        repository.deleteById(id);
    }
}
