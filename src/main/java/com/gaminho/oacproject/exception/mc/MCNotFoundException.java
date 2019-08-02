package com.gaminho.oacproject.exception.mc;

public class MCNotFoundException extends MCException {

    private long idMC;

    public MCNotFoundException(long pIdMC) {
        super(MCException.MC_NOT_FOUND_CODE);
        idMC = pIdMC;
    }

    @Override
    public String getExceptionMessage() {
        return String.format("MC with id %d does not exist.", this.idMC);
    }

}
