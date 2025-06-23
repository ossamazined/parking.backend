package com.pfe.parking_app.exception;

public class EntrepriseNotFoundException extends RuntimeException{

    public EntrepriseNotFoundException(String nomEnreprise) {
        super("Could not find the Client with the nomEnreprise: " + nomEnreprise);
    }

}
