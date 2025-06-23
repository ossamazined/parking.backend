package com.pfe.parking_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.parking_app.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long>{

    Optional<Parking> findByNom(String nom);

}
