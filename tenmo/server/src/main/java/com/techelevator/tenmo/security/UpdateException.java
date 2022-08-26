package com.techelevator.tenmo.security;

public class UpdateException extends RuntimeException{

    public UpdateException(String errorMessage, Throwable err) {

        super(errorMessage, err);
    }

    public UpdateException(String errorMessage) {

        super(errorMessage);
    }

}
