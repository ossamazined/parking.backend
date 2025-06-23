package com.pfe.parking_app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Double longitude;
    private Double latitude;
    private String adresse;
    private String description;
    private int capacite;
    private Double tarif;
    private String image = "./images/voitures-parking-rabat.jpg";

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parking")
    private List<Emplacement> emplacements = new ArrayList<>();

    @ManyToMany(mappedBy = "favoris")
    @JsonIgnoreProperties("favoris")
    private List<Automobiliste> automobilistes;

    @ManyToOne
    @JsonIgnore
    private Entreprise entreprise;

    // Nouveau champ pour gérer l'état du parking
    private boolean active = true; // Le parking est actif par défaut

    // Constructeurs
    public Parking(String nom, Double longitude, Double latitude, int capacite, Double tarif, String adresse,
            String description, String image, Entreprise entreprise) {
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacite = capacite;
        this.tarif = tarif;
        this.adresse = adresse;
        this.description = description;
        this.image = image;
        this.entreprise = entreprise;
        initializeEmplacements();
    }

    public Parking() {
    }

    // Méthode pour initialiser les emplacements
    public void initializeEmplacements() {
        int currentSize = emplacements.size();
        if (capacite > currentSize) {
            // Ajouter de nouveaux emplacements
            for (int i = currentSize + 1; i <= capacite; i++) {
                Emplacement emplacement = new Emplacement(i, this, "libre");
                emplacements.add(emplacement);
            }
        } else if (capacite < currentSize) {
            // Supprimer les emplacements en excès
            emplacements.subList(capacite, currentSize).clear();
        }
    }

    // Getters et setters
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int newCapacite) {
        if (newCapacite > this.capacite) {
            // Augmenter la capacité: Ajouter de nouveaux emplacements
            for (int i = this.capacite + 1; i <= newCapacite; i++) {
                Emplacement emplacement = new Emplacement(i, this, "libre");
                emplacements.add(emplacement);
            }
        } else if (newCapacite < this.capacite) {
            // Réduire la capacité: Supprimer les emplacements en excès
            emplacements.removeIf(e -> e.getNumero() > newCapacite && e.getEtat().equals("libre"));
        }
        this.capacite = newCapacite;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Emplacement> getEmplacements() {
        return emplacements;
    }

    public void setEmplacements(List<Emplacement> emplacements) {
        this.emplacements = emplacements;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }


    // Méthode pour désactiver un parking
    public void desactiverParking() {
        this.active = false;
    }

    // Méthode pour réactiver un parking
    public void reactiverParking() {
        this.active = true;
    }
}
