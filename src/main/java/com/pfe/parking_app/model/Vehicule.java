package com.pfe.parking_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String matricule;

    @ManyToOne
    @JsonIgnoreProperties({"vehicules", "reservations", "favoris"})
    private Automobiliste automobiliste;

    // Constructors, Getters, and Setters

    public Vehicule(String marque, String matricule, Automobiliste automobiliste) {
        this.marque = marque;
        this.matricule = matricule;
        this.automobiliste = automobiliste;
    }

    public Vehicule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Automobiliste getAutomobiliste() {
        return automobiliste;
    }

    public void setAutomobiliste(Automobiliste automobiliste) {
        this.automobiliste = automobiliste;
    }
}


/*
package com.pfe.parking_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    
    @Column(unique = true)
    private String matricule;

    @ManyToOne
    @JsonIgnore
    private Automobiliste automobiliste;

    public Vehicule(String marque, String matricule) {
        this.marque = marque;
        this.matricule = matricule;
    }

    public Vehicule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Automobiliste getAutomobiliste() {
        return automobiliste;
    }

    public void setAutomobiliste(Automobiliste automobiliste) {
        this.automobiliste = automobiliste;
    }
}

*/