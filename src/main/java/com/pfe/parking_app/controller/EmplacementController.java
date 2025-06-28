package com.pfe.parking_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pfe.parking_app.exception.EmplacementNotFoundException;
import com.pfe.parking_app.model.Emplacement;
import com.pfe.parking_app.repository.EmplacementRepository;

@RestController
@CrossOrigin(origins = "*")
public class EmplacementController {

    @Autowired
    private EmplacementRepository emplacementRepository;

    // Récupère la liste des emplacements d'un parking
    @GetMapping("parking/{parkingId}/availability")
    public List<Emplacement> getAvailability(@PathVariable Long parkingId) {
        return emplacementRepository.findByParkingId(parkingId);
    }

    // Change l'état d'un emplacement (libre ou occupe)
    @PutMapping("emplacement/{id}")
    public Emplacement changeEtat(@PathVariable Long id, @RequestBody EtatRequest etatRequest) {
        return emplacementRepository.findById(id)
            .map(emplacement -> {
                emplacement.setEtat(etatRequest.getEtat()); // "libre" ou "occupe"
                return emplacementRepository.save(emplacement);
            })
            .orElseThrow(() -> new EmplacementNotFoundException(id));
    }

    // Classe interne pour récupérer le body JSON
    public static class EtatRequest {
        private String etat;

        public String getEtat() {
            return etat;
        }

        public void setEtat(String etat) {
            this.etat = etat;
        }
    }
}
