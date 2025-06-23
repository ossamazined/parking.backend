package com.pfe.parking_app.exception;

public class EmplacementNotFoundException extends RuntimeException{

    public EmplacementNotFoundException(Long id) {
        super("Could not find the Emplacement with the id: " + id);
    }

}
