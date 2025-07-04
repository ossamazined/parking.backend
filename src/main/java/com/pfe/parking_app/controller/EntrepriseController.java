package com.pfe.parking_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.parking_app.exception.ErrorMessage;
import com.pfe.parking_app.model.Entreprise;
import com.pfe.parking_app.model.Parking;
import com.pfe.parking_app.model.Reservation;
import com.pfe.parking_app.repository.EntrepriseRepository;
import com.pfe.parking_app.repository.ParkingRepository;
import com.pfe.parking_app.repository.ReservationRepository;

@RestController
@CrossOrigin(origins = "*")
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // To create a new Entreprise
    @PostMapping("/entreprise")
    Entreprise addEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    // Connexion
    @PostMapping("/loginentreprise")
    public ResponseEntity<?> loginEntreprise(@RequestBody Entreprise loginDetails) {
        Entreprise entreprise = entrepriseRepository.findByEmail(loginDetails.getEmail());
        if (entreprise != null && entreprise.getMotdepasse().equals(loginDetails.getMotdepasse())) {
            return ResponseEntity.ok(entreprise);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Invalid credentials"));
    }

    // Ajouter un nouveau parking
    @PostMapping("/entreprise/{id}/parking")
    public ResponseEntity<?> addParking(@PathVariable Long id, @RequestBody Parking parking) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);

        if (entrepriseOpt.isPresent()) {
            Entreprise entreprise = entrepriseOpt.get();

            parking.setEntreprise(entreprise);
            parkingRepository.save(parking);

            entreprise.getParkings().add(parking);
            entrepriseRepository.save(entreprise);

            return ResponseEntity.ok(entreprise);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Entreprise not found"));
    }

    // Get all the parkings of an entreprise
    @GetMapping("/entreprise/{id}/parkings")
    public ResponseEntity<?> getParkings(@PathVariable Long id) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
        if (entrepriseOpt.isPresent()) {
            Entreprise entreprise = entrepriseOpt.get();
            return ResponseEntity.ok(entreprise.getParkings());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Parkings not found"));
    }

    // Get all pending reservations of an entreprise
    @GetMapping("/entreprise/{id}/reservations/pending")
    public ResponseEntity<?> getPendingReservations(@PathVariable Long id) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
        if (entrepriseOpt.isPresent()) {
            List<Reservation> pendingReservations = reservationRepository.findByEntrepriseIdAndEtatReservation(id,
                    "en attente");
            return ResponseEntity.ok(pendingReservations);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Entreprise not found"));
    }

    // Modifier profile
    @PutMapping("/entreprise/{id}")
    public ResponseEntity<?> updateEntreprise(@RequestBody Entreprise newEntreprise, @PathVariable Long id) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
        if (entrepriseOpt.isPresent()) {
            Entreprise entreprise = entrepriseOpt.get();
            entreprise.setNomEntreprise(newEntreprise.getNomEntreprise());
            entreprise.setAdresse(newEntreprise.getAdresse());
            entreprise.setTel(newEntreprise.getTel());
            entreprise.setEmail(newEntreprise.getEmail());
            entrepriseRepository.save(entreprise);
            return ResponseEntity.ok(entreprise);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The entreprise with id " + id + " was not found"));
        }
    }

    // Modifier le mot de passe
    @PutMapping("/entreprisepass/{id}")
    public ResponseEntity<?> updateEntreprisePassword(@RequestBody Entreprise newEntreprise, @PathVariable Long id) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
        if (entrepriseOpt.isPresent()) {
            Entreprise entreprise = entrepriseOpt.get();
            entreprise.setMotdepasse(newEntreprise.getMotdepasse());
            entrepriseRepository.save(entreprise);
            return ResponseEntity.ok(entreprise);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The entreprise with id " + id + " was not found"));
        }
    }

    // Supprimer entreprise
    @DeleteMapping("/entreprise/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);

        if (entreprise.isPresent()) {
            entrepriseRepository.deleteById(id);
            return ResponseEntity.ok("entreprise with the id " + id + " has been deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The entreprise with id " + id + " was not found");
        }
    }

    // Get all the reservations of an entreprise
    @GetMapping("/entreprise/{id}/reservations")
    public ResponseEntity<?> getReservations(@PathVariable Long id) {
        Optional<Entreprise> entrepriseOpt = entrepriseRepository.findById(id);
        if (entrepriseOpt.isPresent()) {
            List<Reservation> reservations = reservationRepository.findByEntrepriseId(id);
            return ResponseEntity.ok(reservations);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Entreprise not found"));
    }
}