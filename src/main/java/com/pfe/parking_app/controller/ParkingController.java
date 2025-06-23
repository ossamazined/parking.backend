package com.pfe.parking_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pfe.parking_app.exception.ErrorMessage;
import com.pfe.parking_app.exception.ParkingNotFoundException;
import com.pfe.parking_app.model.Parking;
import com.pfe.parking_app.repository.ParkingRepository;

@RestController
@CrossOrigin(origins = "*")
public class ParkingController {

    @Autowired
    private ParkingRepository parkingRepository;

    // To create a new parking
    @PostMapping("/parking")
    public Parking addParking(@RequestBody Parking parking) {
        return parkingRepository.save(parking);
    }

    // To get all parkings
    @GetMapping("/parkings")
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    // To get a parking by name
    @GetMapping("/parking/{nom}")
    public Parking getParking(@PathVariable String nom) {
        return parkingRepository.findByNom(nom)
                .orElseThrow(() -> new ParkingNotFoundException(nom));
    }

    // To update a parking
    @PutMapping("/parking/{id}")
    public ResponseEntity<?> updateParking(@RequestBody Parking newParking, @PathVariable Long id) {
        Optional<Parking> parkingOpt = parkingRepository.findById(id);
        if (parkingOpt.isPresent()) {
            Parking parking = parkingOpt.get();
            parking.setNom(newParking.getNom());
            parking.setLongitude(newParking.getLongitude());
            parking.setLatitude(newParking.getLatitude());
            parking.setCapacite(newParking.getCapacite());
            parking.setTarif(newParking.getTarif());
            parking.setAdresse(newParking.getAdresse());
            parking.setDescription(newParking.getDescription());
            parkingRepository.save(parking);
            return ResponseEntity.ok(parking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The parking with id " + id + " was not found"));
        }
    }

    // To deactivate a parking
    @PutMapping("/parking/deactivate/{id}")
    public ResponseEntity<?> deactivateParking(@PathVariable Long id) {
        Optional<Parking> parkingOpt = parkingRepository.findById(id);
        if (parkingOpt.isPresent()) {
            Parking parking = parkingOpt.get();
            // Mark the parking as deactivated (set 'active' to false)
            parking.setActive(false);
            parkingRepository.save(parking);
            return ResponseEntity.ok("Parking with ID: " + id + " has been deactivated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The parking with id " + id + " was not found"));
        }
    }
    
    // To activate a parking
    @PutMapping("/parking/activate/{id}")
    public ResponseEntity<?> activateParking(@PathVariable Long id) {
        Optional<Parking> parkingOpt = parkingRepository.findById(id);
        if (parkingOpt.isPresent()) {
            Parking parking = parkingOpt.get();
            // Mark the parking as deactivated (set 'active' to false)
            parking.setActive(true);
            parkingRepository.save(parking);
            return ResponseEntity.ok("Parking with ID: " + id + " has been activated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The parking with id " + id + " was not found"));
        }
    }
}


/*
package com.pfe.parking_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.parking_app.exception.ParkingNotFoundException;
import com.pfe.parking_app.model.Parking;
import com.pfe.parking_app.repository.ParkingRepository;




@RestController
@CrossOrigin(origins = "*")
public class ParkingController {

    
    @Autowired
    private ParkingRepository parkingRepository;

    //To create a new parking
    @PostMapping("/parking")
    Parking addParking(@RequestBody Parking parking) {
        return parkingRepository.save(parking);
    }

    //To get all parkings
    @GetMapping("/parkings")
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    //To get a parking by name
    @GetMapping("/parking/{nom}")
    public Parking getParking(@PathVariable String nom) {
        return parkingRepository.findByNom(nom)
            .orElseThrow(() -> new ParkingNotFoundException(nom));
    }

    //To update a parking
    @PutMapping("parking/{nom}")
    public Parking updateParking(@RequestBody Parking newparking, @PathVariable String nom){

        return parkingRepository.findByNom(nom)
        .map(parking ->{
            parking.setNom(newparking.getNom());
            parking.setLongitude(newparking.getLongitude());
            parking.setLatitude(newparking.getLatitude());
            parking.setCapacite(newparking.getCapacite());
            parking.setTarif(newparking.getTarif());
            parking.setAdresse(newparking.getAdresse());
            parking.setDescription(parking.getDescription());

            return parkingRepository.save(parking);

        }).orElseThrow(()-> new ParkingNotFoundException(nom));
    }

    //To delete a parking
    @DeleteMapping("/deleteparking/{nom}")
    public String deleteParking(@PathVariable String nom){
        //find the parking by its name
        Optional<Parking> parking = parkingRepository.findByNom(nom);

        if(parking.isPresent()){
            Long id = parking.get().getId();
            parkingRepository.deleteById(id);

            return "Parking with the name: "+nom+" has been deleted!";
        }

        else{
            throw new ParkingNotFoundException(nom);
        }
    }
    
}
*/