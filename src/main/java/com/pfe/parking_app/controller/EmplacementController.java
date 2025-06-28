package com.pfe.parking_app.controller;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.parking_app.exception.EmplacementNotFoundException;
import com.pfe.parking_app.model.Emplacement;
// import com.pfe.parking_app.model.Reservation;
import com.pfe.parking_app.repository.EmplacementRepository;
// import com.pfe.parking_app.repository.ReservationRepository;

@RestController
@CrossOrigin(origins = "*")
public class EmplacementController {

    @Autowired
    private EmplacementRepository emplacementRepository;

    // @Autowired
    // private ReservationRepository reservationRepository;

   
    @GetMapping("parking/{parkingId}/availability")
        public List<Emplacement> getAvailability(@PathVariable Long parkingId ){ //, @RequestParam String date, @RequestParam String heureDebut) {
                // Fetch all emplacements for the given parking
            List<Emplacement> emplacements =emplacementRepository.findByParkingId(parkingId);
     
    //          //Fetch all reservations for the given parking, date, and time
    //         List<Reservation> reservations =
    //         reservationRepository.findByParkingIdAndDateAndHeureDebut(parkingId, date, heureDebut);
     
    //  //Update the status of emplacements based on reservations
    //  for (Reservation reservation : reservations) {
    //         for (Emplacement emplacement : emplacements) {
    //             if (reservation.getEmplacement().getId().equals(emplacement.getId())) {
    //                 emplacement.setEtat("occupe");
    //             }
    //     }
    //     }
        return emplacements;
        }
     
     

    //  @GetMapping("parking/{parkingId}/availability")
    //  public List<Emplacement> getAvailability(@PathVariable Long parkingId, @RequestParam(required = false) String date, @RequestParam(required = false) String heureDebut, @RequestParam(required = false) int duree) {
    //      // Parse the date and heureDebut to LocalDateTime objects
    //      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
    //      LocalDateTime requestedStartTime = LocalDateTime.parse(date + " " + heureDebut, formatter);
    //      LocalDateTime requestedEndTime = requestedStartTime.plusHours(duree);
     
    //      // Fetch all emplacements for the given parking
    //      List<Emplacement> emplacements = emplacementRepository.findByParkingId(parkingId);
     
    //      // Fetch all reservations for the given parking and date
    //      List<Reservation> reservations = reservationRepository.findByParkingIdAndDate(parkingId, date);
     
    //      // Update the status of emplacements based on reservations
    //      for (Reservation reservation : reservations) {
    //          // Parse the reservation's start time and calculate its end time
    //          LocalDateTime reservationStartTime = LocalDateTime.parse(reservation.getDate() + " " + reservation.getHeureDebut(), formatter);
    //          LocalDateTime reservationEndTime = reservationStartTime.plusHours(reservation.getDuree());
     
    //          // Check if the requested period overlaps with the reservation period
    //          if (requestedStartTime.isBefore(reservationEndTime) && requestedEndTime.isAfter(reservationStartTime)) {
    //              // Mark the emplacement as occupied
    //              for (Emplacement emplacement : emplacements) {
    //                  if (reservation.getEmplacement().getId().equals(emplacement.getId())) {
    //                      emplacement.setEtat("occupe");
    //                  }
    //              }
    //          }
    //      }
    //      return emplacements;
    //  }
     

    @PutMapping("emplacement/{id}")
    Emplacement changeEtat_occupe(@PathVariable Long id) {
        return emplacementRepository.findById(id)
                .map(emplacement -> {
                    emplacement.setEtat("occupe");

                    return emplacementRepository.save(emplacement);
                }).orElseThrow(() -> new EmplacementNotFoundException(id));
    }

    @PutMapping("emplacement_liberer/{id}")
    Emplacement changeEtat_libre(@PathVariable Long id) {
        return emplacementRepository.findById(id)
                .map(emplacement -> {
                    emplacement.setEtat("libre");

                    return emplacementRepository.save(emplacement);
                }).orElseThrow(() -> new EmplacementNotFoundException(id));
    }
}
