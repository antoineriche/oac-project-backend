package com.gaminho.oacproject.exception.mc;

import com.gaminho.oacproject.exception.OACProjectException;

public abstract class MCException extends OACProjectException {

    public static final int MC_NOT_FOUND_CODE = 1;
    public static final int INVALID_MC_CODE = 2;
    public static final int NO_MC_CODE = 3;

    public MCException(int exceptionCode) {
        super(exceptionCode);
    }
}
