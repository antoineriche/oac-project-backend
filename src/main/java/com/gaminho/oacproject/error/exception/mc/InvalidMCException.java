package com.gaminho.oacproject.error.exception.mc;

import com.gaminho.oacproject.error.exception.CRUDException;

public class InvalidMCException extends CRUDException {

    public InvalidMCException() {
        super(CRUDException.INVALID_DATA_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return invalidDataMessage("MC");
    }
}
