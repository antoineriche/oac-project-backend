package com.gaminho.oacproject.error.exception.project;


import com.gaminho.oacproject.error.exception.CRUDException;

public class NoTypeException extends CRUDException {

    public NoTypeException() {
        super(CRUDException.NO_DATA_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return noDataMessage("project type");
    }
}
