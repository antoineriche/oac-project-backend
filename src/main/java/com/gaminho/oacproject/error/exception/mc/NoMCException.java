package com.gaminho.oacproject.error.exception.mc;

import com.gaminho.oacproject.error.exception.CRUDException;

public class NoMCException extends CRUDException {

    public NoMCException() {
        super(CRUDException.NO_DATA_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return noDataMessage("MC");
    }
}
