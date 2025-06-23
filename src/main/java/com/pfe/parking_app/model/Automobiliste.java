
package com.pfe.parking_app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Automobiliste extends Personne {

    private boolean active = true;

    @OneToMany(mappedBy = "automobiliste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("automobiliste")
    private List<Vehicule> vehicules;

    @OneToMany(mappedBy = "automobiliste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("automobiliste")
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable(name = "favoris",
        joinColumns = @JoinColumn(name = "automobiliste_id"),
        inverseJoinColumns = @JoinColumn(name = "parking_id")
    )
    @JsonIgnoreProperties("automobilistes")
    private List<Parking> favoris;

    public Automobiliste(String nom, String prenom, String cni, String tel, String email, String motdepasse) {
        super(nom, prenom, cni, tel, email, motdepasse);
    }

    public Automobiliste() {
    }

    // Getters and Setters

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Parking> getFavoris() {
        return favoris;
    }

    public void setFavoris(List<Parking> favoris) {
        this.favoris = favoris;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}