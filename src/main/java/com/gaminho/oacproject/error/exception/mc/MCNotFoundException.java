package com.gaminho.oacproject.error.exception.mc;

import com.gaminho.oacproject.error.exception.CRUDException;

public class MCNotFoundException extends CRUDException {

    private long idMC;

    public MCNotFoundException(long pIdMC) {
        super(CRUDException.DATA_NOT_FOUND_CODE);
        idMC = pIdMC;
    }

    @Override
    public String getExceptionMessage() {
        return dataNotFoundMessage("MC", this.idMC);
    }

}
