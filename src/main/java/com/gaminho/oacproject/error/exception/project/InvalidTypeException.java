package com.gaminho.oacproject.error.exception.project;

import com.gaminho.oacproject.error.exception.CRUDException;

public class InvalidTypeException extends CRUDException {

    public InvalidTypeException() {
        super(CRUDException.INVALID_DATA_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return invalidDataMessage("project type");
    }

}
