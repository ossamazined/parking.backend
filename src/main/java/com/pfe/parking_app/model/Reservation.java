package com.pfe.parking_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String heureDebut;
    private int duree;

    private String etatReservation = "Payee";
    private Double prixTotal;

    @Column(unique = true)
    private String code;

    private String matricule;

    @ManyToOne
    @JoinColumn(name = "emplacement_id")
    private Emplacement emplacement;

    @ManyToOne
    @JoinColumn(name = "automobiliste_id")
    @JsonIgnoreProperties({"reservations", "favoris", "vehicules"})
    private Automobiliste automobiliste;

    public Reservation(String date, String heureDebut, int duree, Double prixTotal, String matricule, String code, Emplacement emplacement, Automobiliste automobiliste) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.prixTotal = prixTotal;
        this.matricule = matricule;
        this.code = code;
        this.emplacement = emplacement;
        this.automobiliste = automobiliste;
    }

    public Reservation() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getEtatReservation() {
        return etatReservation;
    }

    public void setEtatReservation(String etatReservation) {
        this.etatReservation = etatReservation;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Automobiliste getAutomobiliste() {
        return automobiliste;
    }

    public void setAutomobiliste(Automobiliste automobiliste) {
        this.automobiliste = automobiliste;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}