package com.pfe.parking_app.exception;

public class ReservationNoutFoundException extends RuntimeException{

    public ReservationNoutFoundException(Long id){
        super("Could not find the User with the CNI: " + id);
    }

}
