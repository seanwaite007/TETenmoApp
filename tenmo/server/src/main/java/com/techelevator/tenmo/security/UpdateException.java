package com.techelevator.tenmo.security;

import java.math.BigDecimal;

public class UpdateException extends RuntimeException{

    public UpdateException(String errorMessage) {
        super(errorMessage);

    }

}
