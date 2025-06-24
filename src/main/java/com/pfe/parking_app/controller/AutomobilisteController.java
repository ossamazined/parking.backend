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
import com.pfe.parking_app.model.Automobiliste;
import com.pfe.parking_app.model.Parking;
import com.pfe.parking_app.model.Reservation;
import com.pfe.parking_app.model.Vehicule;
import com.pfe.parking_app.repository.AutomobilisteRepository;
import com.pfe.parking_app.repository.ParkingRepository;
import com.pfe.parking_app.repository.ReservationRepository;
import com.pfe.parking_app.repository.VehiculeRepository;

@RestController
@CrossOrigin(origins = "*")
public class AutomobilisteController {

    @Autowired
    private AutomobilisteRepository automobilisteRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Inscription d'un automobiliste
    @PostMapping("/automobiliste")
    Automobiliste addAutomobiliste(@RequestBody Automobiliste automobiliste) {
        return automobilisteRepository.save(automobiliste);
    }

    // Connexion
    @PostMapping("/loginautomobiliste")
    public ResponseEntity<?> loginAutomobiliste(@RequestBody Automobiliste loginDetails) {
        Automobiliste automobiliste = automobilisteRepository.findByEmail(loginDetails.getEmail());
        if (automobiliste != null && automobiliste.isActive() && automobiliste.getMotdepasse().equals(loginDetails.getMotdepasse())) {
            return ResponseEntity.ok(automobiliste);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Invalid credentials"));
    }

    // Add parking to favoris
    @PostMapping("/automobiliste/{id}/favoris/{parkingId}")
    public ResponseEntity<?> addFavoris(@PathVariable Long id, @PathVariable Long parkingId) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

        if (automobilisteOpt.isPresent() && parkingOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            Parking parking = parkingOpt.get();

            automobiliste.getFavoris().add(parking);
            automobilisteRepository.save(automobiliste);

            return ResponseEntity.ok(automobiliste);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Automobiliste or Parking not found"));
    }

    // Delete parking from favoris
    @DeleteMapping("/automobiliste/{id}/favoris/{parkingId}")
    public ResponseEntity<?> deleteFavoris(@PathVariable Long id, @PathVariable Long parkingId) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        Optional<Parking> parkingOpt = parkingRepository.findById(parkingId);

        if (automobilisteOpt.isPresent() && parkingOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            Parking parking = parkingOpt.get();

            automobiliste.getFavoris().remove(parking);
            automobilisteRepository.save(automobiliste);

            return ResponseEntity.ok(automobiliste);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Automobiliste or Parking not found"));
    }

    // Ajouter un vehicule
    @PostMapping("/automobiliste/{id}/vehicule")
    public ResponseEntity<?> addVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);

        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();

            vehicule.setAutomobiliste(automobiliste);
            vehiculeRepository.save(vehicule);

            automobiliste.getVehicules().add(vehicule);
            automobilisteRepository.save(automobiliste);

            return ResponseEntity.ok(automobiliste);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Automobiliste not found"));
    }

    // Supprimer un vehicule
    @DeleteMapping("/automobiliste/{id}/vehicule/{vehiculeId}")
    public ResponseEntity<?> deleteVehicule(@PathVariable Long id, @PathVariable Long vehiculeId) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        Optional<Vehicule> vehiculeOpt = vehiculeRepository.findById(vehiculeId);

        if (automobilisteOpt.isPresent() && vehiculeOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            Vehicule vehicule = vehiculeOpt.get();

            automobiliste.getVehicules().remove(vehicule);
            vehiculeRepository.deleteById(vehiculeId);
            automobilisteRepository.save(automobiliste);

            return ResponseEntity.ok(automobiliste);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("Automobiliste or Vehicule not found"));
    }

    // get the vehicules
    @GetMapping("/automobiliste/{id}/vehicules")
    public ResponseEntity<?> getVehicules(@PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            return ResponseEntity.ok(automobiliste.getVehicules());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Vehicules not found"));
    }

    // get favoris parkings of an automobiliste
    @GetMapping("/automobiliste/{id}/favoris")
    public ResponseEntity<?> getFavorites(@PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            return ResponseEntity.ok(automobiliste.getFavoris());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Automobiliste not found"));
    }

    // Get the reservations of an automobiliste where etatReservation = "payee"
    @GetMapping("/automobiliste/{id}/reservations")
    public ResponseEntity<?> getReservations(@PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            List<Reservation> payeeReservations = reservationRepository.findByAutomobilisteIdAndEtatReservation(id, "payee");
            return ResponseEntity.ok(payeeReservations);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Automobiliste not found"));
    }


    /*
     // get the reservations of an automobiliste
     
     @GetMapping("/automobiliste/{id}/reservations")
     public ResponseEntity<?> getReservations(@PathVariable Long id) {
     Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
     if (automobilisteOpt.isPresent()) {
        Automobiliste automobiliste = automobilisteOpt.get();
        return ResponseEntity.ok(automobiliste.getReservations());
     }
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
     ErrorMessage("Automobiliste not found"));
     }
     
     */

    // Modifer profile
    @PutMapping("/automobiliste/{id}")
    public ResponseEntity<?> updateAutomobiliste(@RequestBody Automobiliste newAutomobiliste, @PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            automobiliste.setNom(newAutomobiliste.getNom());
            automobiliste.setPrenom(newAutomobiliste.getPrenom());
            automobiliste.setTel(newAutomobiliste.getTel());
            automobiliste.setEmail(newAutomobiliste.getEmail());
            automobiliste.setCni(newAutomobiliste.getCni());
            automobilisteRepository.save(automobiliste);
            return ResponseEntity.ok(automobiliste);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The automobiliste with id " + id + " was not found"));
        }
    }

    // Modifer le mot de passe
    @PutMapping("/automobilistepass/{id}")
    public ResponseEntity<?> updateAutomobilistePassword(@RequestBody Automobiliste newAutomobiliste,
            @PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            automobiliste.setMotdepasse(newAutomobiliste.getMotdepasse());
            automobilisteRepository.save(automobiliste);
            return ResponseEntity.ok(automobiliste);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The automobiliste with id " + id + " was not found"));
        }
    }

    // Supprimer automobiliste
    @DeleteMapping("/automobiliste/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<Automobiliste> automobiliste = automobilisteRepository.findById(id);

        if (automobiliste.isPresent()) {
            automobilisteRepository.deleteById(id);
            return ResponseEntity.ok("Automobiliste with the id " + id + " has been deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The automobiliste with id " + id + " was not found");
        }
    }


    @PutMapping("/automobiliste/{id}/deactivate")
    public ResponseEntity<?> deactivateAccount(@PathVariable Long id) {
        Optional<Automobiliste> automobilisteOpt = automobilisteRepository.findById(id);
        if (automobilisteOpt.isPresent()) {
            Automobiliste automobiliste = automobilisteOpt.get();
            automobiliste.setActive(false); // Deactivate the account
            automobilisteRepository.save(automobiliste);
            return ResponseEntity.ok("Automobiliste account deactivated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The automobiliste with id " + id + " was not found");
        }
    }

    /*
      // Delete reservation from automobiliste
      
      @DeleteMapping("/automobiliste/{id}/reservations/{reservationId}")
      public ResponseEntity<?> deleteReservation(@PathVariable Long
      id, @PathVariable Long reservationId) {
      Optional<Automobiliste> automobilisteOpt =
      automobilisteRepository.findById(id);
      Optional<Reservation> reservationOpt =
      reservationRepository.findById(reservationId);
      
      if (automobilisteOpt.isPresent() && reservationOpt.isPresent()) {
      Automobiliste automobiliste = automobilisteOpt.get();
      Reservation reservation = reservationOpt.get();
      
      if (automobiliste.getReservations().remove(reservation)) {
      automobilisteRepository.save(automobiliste);
      //reservationRepository.delete(reservation);
      return ResponseEntity.ok(automobiliste);
      }
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(new
      ErrorMessage("Reservation not found in automobiliste's reservations"));
      }
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(new ErrorMessage("Automobiliste or Reservation not found"));
      }
      
     */

}

/*
 * 
 * //To get all the automobilistes
 * 
 * @GetMapping("/automobilistes")
 * java.util.List<User> getAllUsers() {
 * return automobilisteRepository.findAll();
 * }
 * 
 * 
 * //To get a user by his cni
 * 
 * @GetMapping("/getuser/{cni}")
 * 
 * @CrossOrigin(origins = "*")
 * User getUserByCni(@PathVariable String cni) {
 * return automobilisteRepository.findByCni(cni)
 * .orElseThrow(()-> new UserNotFoundException(cni));
 * }
 * 
 * 
 * //Connexion
 * //@GetMapping("/getuser/{email}")
 * 
 * @PutMapping("updateuser/{cni}")
 * User updateUser(@RequestBody User newUser,@PathVariable String cni){
 * 
 * return automobilisteRepository.findByCni(cni)
 * .map(user ->{
 * user.setCni(newUser.getCni());
 * user.setNom(newUser.getNom());
 * user.setPrenom(newUser.getPrenom());
 * user.setTel(newUser.getTel());
 * user.setEmail(newUser.getEmail());
 * user.setMotdepasse(newUser.getMotdepasse());
 * 
 * return automobilisteRepository.save(user);
 * }).orElseThrow(()-> new UserNotFoundException(cni));
 * 
 * }
 * 
 * 
 * @PutMapping("/automobiliste/{automobilisteId}/annulerReservation/{id}")
 * public ResponseEntity<?> annulerReservationAndRemoveFromFavoris(@PathVariable
 * Long automobilisteId,
 * 
 * @PathVariable Long id) {
 * Optional<Automobiliste> automobilisteOpt =
 * automobilisteRepository.findById(automobilisteId);
 * Optional<Reservation> reservationOpt = reservationRepository.findById(id);
 * 
 * if (automobilisteOpt.isPresent() && reservationOpt.isPresent()) {
 * Automobiliste automobiliste = automobilisteOpt.get();
 * Reservation reservation = reservationOpt.get();
 * 
 * // Set the reservation state to "annulée"
 * reservation.setEtatReservation("annulée");
 * 
 * // Remove the reservation from the Automobiliste's reservations list
 * automobiliste.getReservations().remove(reservation);
 * 
 * // Save changes
 * reservationRepository.save(reservation);
 * automobilisteRepository.save(automobiliste);
 * 
 * return ResponseEntity.ok(reservation);
 * }
 * 
 * return ResponseEntity.status(HttpStatus.NOT_FOUND)
 * .body(new ErrorMessage("Automobiliste or Reservation not found"));
 * }
 * 
 * 
 * 
 */
