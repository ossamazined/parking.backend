package com.pfe.parking_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfe.parking_app.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

     @Query("SELECT r FROM Reservation r WHERE r.emplacement.parking.id = :parkingId AND r.date = :date AND r.heureDebut = :heureDebut")
    List<Reservation> findByParkingIdAndDateAndHeureDebut(@Param("parkingId") Long parkingId, 
                     @Param("date") String date, @Param("heureDebut") String heureDebut);

    @Query("SELECT r FROM Reservation r WHERE r.emplacement.parking.id = :parkingId AND r.date = :date")
    List<Reservation> findByParkingIdAndDate(@Param("parkingId") Long parkingId, @Param("date") String date);


    List<Reservation> findByAutomobilisteIdAndEtatReservation(Long automobilisteId, String etatReservation);

    @Query("SELECT r FROM Reservation r JOIN r.emplacement e JOIN e.parking p JOIN p.entreprise en WHERE en.id = :entrepriseId")
    List<Reservation> findByEntrepriseId(@Param("entrepriseId") Long entrepriseId);

    List<Reservation> findByAutomobilisteId(Long id);
}


