package com.gaminho.oacproject.exception.project;


import com.gaminho.oacproject.exception.CRUDException;

public class NoTypeException extends CRUDException {

    public NoTypeException() {
        super(CRUDException.NO_DATA_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return noDataMessage("project type");
    }
}
