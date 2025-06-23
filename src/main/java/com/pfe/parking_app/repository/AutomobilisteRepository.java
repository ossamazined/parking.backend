package com.pfe.parking_app.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.parking_app.model.Automobiliste;

public interface AutomobilisteRepository extends JpaRepository<Automobiliste, Long>{

    Automobiliste findByEmail(String email);
}
