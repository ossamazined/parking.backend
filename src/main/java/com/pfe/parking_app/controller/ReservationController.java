package com.pfe.parking_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.parking_app.exception.ErrorMessage;
import com.pfe.parking_app.model.Emplacement;
import com.pfe.parking_app.model.Reservation;
import com.pfe.parking_app.repository.ReservationRepository;
import com.pfe.parking_app.repository.EmplacementRepository;

@RestController
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmplacementRepository emplacementRepository;

    // To add a new reservation
    @PostMapping("/reservation")
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
        // Ensure new reservation starts as "en attente"
        reservation.setEtatReservation("en attente");

        // Validate emplacement exists
        Optional<Emplacement> emplacementOpt = emplacementRepository.findById(reservation.getEmplacement().getId());
        if (!emplacementOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage("Emplacement not found"));
        }

        // Save reservation
        Reservation savedReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    // To get all reservations
    @GetMapping("/reservations")
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    // Edit a reservation by id
    @PutMapping("/reservation/{id}")
    public ResponseEntity<?> editReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            Optional<Emplacement> emplacementOpt = emplacementRepository
                    .findById(newReservation.getEmplacement().getId());
            if (!emplacementOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorMessage("Emplacement not found"));
            }

            reservation.setDate(newReservation.getDate());
            reservation.setHeureDebut(newReservation.getHeureDebut());
            reservation.setDuree(newReservation.getDuree());
            reservation.setPrixTotal(newReservation.getPrixTotal());
            reservation.setMatricule(newReservation.getMatricule());
            reservation.setCode(newReservation.getCode());
            reservation.setEmplacement(newReservation.getEmplacement());
            reservation.setAutomobiliste(newReservation.getAutomobiliste());
            reservation.setEtatReservation(newReservation.getEtatReservation());

            reservationRepository.save(reservation);
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The reservation with id " + id + " was not found"));
        }
    }

    // Mark a reservation as paid
    @PutMapping("/reservation/{id}/pay")
    public ResponseEntity<?> markAsPaid(@PathVariable Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            if (!reservation.getEtatReservation().equals("en attente")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorMessage("Only pending reservations can be marked as paid"));
            }
            reservation.setEtatReservation("Payee");
            reservationRepository.save(reservation);
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The reservation with id " + id + " was not found"));
        }
    }

    // Cancel a reservation by id
    @PutMapping("/annulerReservation/{id}")
    public ResponseEntity<?> annulerReservation(@PathVariable Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            reservation.setEtatReservation("annulée");
            reservationRepository.save(reservation);
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The reservation with id " + id + " was not found"));
        }
    }
}