package com.gaminho.oacproject.error.exception.project;


import com.gaminho.oacproject.error.exception.CRUDException;

public class TypeNotFoundException extends CRUDException {

    private long idType;

    public TypeNotFoundException(long pIdMC) {
        super(CRUDException.DATA_NOT_FOUND_CODE);
        idType = pIdMC;
    }

    @Override
    public String getExceptionMessage() {
        return dataNotFoundMessage("Project type", this.idType);
    }

}
