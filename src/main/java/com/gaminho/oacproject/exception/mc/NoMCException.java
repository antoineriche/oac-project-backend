package com.gaminho.oacproject.exception.mc;

public class NoMCException extends MCException {

    public NoMCException() {
        super(MCException.NO_MC_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return "No MC.";
    }
}
