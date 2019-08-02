package com.gaminho.oacproject.exception.mc;

import com.gaminho.oacproject.exception.CRUDException;

public class InvalidMCException extends CRUDException {

    public InvalidMCException() {
        super(CRUDException.INVALID_DATA_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return invalidDataMessage("MC");
    }
}
