package com.pfe.parking_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.parking_app.model.Emplacement;

public interface EmplacementRepository extends JpaRepository<Emplacement, Long>{

    List<Emplacement> findByParkingId(Long parkingId);

}
