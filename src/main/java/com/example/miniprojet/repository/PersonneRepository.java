package com.example.miniprojet.repository;

import com.example.miniprojet.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {

    boolean existsByCin(String cin);
}
