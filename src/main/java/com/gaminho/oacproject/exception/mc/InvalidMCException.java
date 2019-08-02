package com.gaminho.oacproject.exception.mc;

public class InvalidMCException extends MCException {

    public InvalidMCException() {
        super(MCException.INVALID_MC_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return "Invalid MC.";
    }
}
