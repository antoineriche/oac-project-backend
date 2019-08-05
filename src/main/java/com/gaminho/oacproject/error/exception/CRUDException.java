package com.gaminho.oacproject.error.exception;

public abstract class CRUDException extends OACProjectException {

    public static final int DATA_NOT_FOUND_CODE = 1;
    public static final int INVALID_DATA_CODE = 2;
    public static final int NO_DATA_CODE = 3;
    public static final int CONSTRAINT_VIOLATION_CODE = 4;

    public CRUDException(int exceptionCode) {
        super(exceptionCode);
    }

    protected String invalidDataMessage(String dataType){
        return String.format("Invalid %s.", dataType);
    }

    protected String noDataMessage(String dataType){
        return String.format("No %s.", dataType);
    }

    protected String dataNotFoundMessage(String dataType, long dataId){
        return String.format("%s with id %d does not exist.", dataType, dataId);
    }
}
