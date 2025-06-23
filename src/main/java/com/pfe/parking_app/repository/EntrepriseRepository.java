package com.pfe.parking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.parking_app.model.Entreprise;
//import java.util.List;


public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

    //<Optional> Entreprise findBynomEntreprise(String nom_entreprise);
    <Optional> Entreprise findByEmail(String email);
}
