package com.gaminho.oacproject.exception;

public abstract class OACProjectException extends RuntimeException {

    private int exceptionCode;

    public OACProjectException(String message) {
        super(message);
    }

//    public OACProjectException(int exceptionCode) {
//        super(getMessage());
//        this.exceptionCode = exceptionCode;
//    }

//    public int getExceptionCode() {
//        return exceptionCode;
//    }

//    public abstract String getMessage(int exceptionCode);
}
