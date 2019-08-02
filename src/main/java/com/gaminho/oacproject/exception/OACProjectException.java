package com.gaminho.oacproject.exception;

public abstract class OACProjectException extends RuntimeException {

    private int exceptionCode;

    public OACProjectException(int exceptionCode) {
        super();
        this.exceptionCode = exceptionCode;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String getMessage() {
        return getExceptionMessage();
    }

    protected abstract String getExceptionMessage();
}
