package com.pfe.parking_app.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Personne {

    public Admin( String nom, String prenom, String cni,
                 String tel, String email, String motdepasse) {
        super( nom, prenom, cni, tel, email, motdepasse);
    }

    public Admin(){
        
    }

}
