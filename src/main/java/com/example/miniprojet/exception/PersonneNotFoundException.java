package com.example.miniprojet.exception;

public class PersonneNotFoundException extends RuntimeException {

    public PersonneNotFoundException(Long id) {
        super("Impossible de trouver la personne avec l'identifiant " + id);
    }
}
