package com.pfe.parking_app.exception;

public class ParkingNotFoundException extends RuntimeException{

    public ParkingNotFoundException(String nom) {
        super("Could not find the User with the nom: " + nom);
    }

}
