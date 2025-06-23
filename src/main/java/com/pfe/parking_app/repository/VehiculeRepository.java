package com.pfe.parking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.parking_app.model.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long>{

}
