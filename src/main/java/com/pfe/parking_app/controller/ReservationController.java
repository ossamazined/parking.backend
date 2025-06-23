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
import com.pfe.parking_app.model.Reservation;
import com.pfe.parking_app.repository.ReservationRepository;

@RestController
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // To add a new reservation
    @PostMapping("/reservation")
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
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

             reservation.setDate(newReservation.getDate());
             reservation.setHeureDebut(newReservation.getHeureDebut());
             reservation.setDuree(newReservation.getDuree());
             reservation.setPrixTotal(newReservation.getPrixTotal());

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

/*
 // To delete a reservation by its id
    @DeleteMapping("/reservation/{id}")
    public String deleteReservation(@PathVariable Long id) {
        if (reservationRepository.findById(id).isPresent()) {
            reservationRepository.deleteById(id);
            return "The reservation with the id : " + id + "has been deleted";
        } else {
            throw new ReservationNoutFoundException(id);
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


     // To get a reservation by id
    @GetMapping("/reservation/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNoutFoundException(id));
    }


*/