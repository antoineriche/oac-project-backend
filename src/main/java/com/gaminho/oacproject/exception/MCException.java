package com.gaminho.oacproject.exception;

public class MCException extends OACProjectException {

    public static final int NO_MC_CODE = 1;
    public static final String NO_MC = "No MCs found";
    public static final String INVALID_MC = "Invalid mc";

    public MCException(String message) {
        super(message);
    }

//    public MCException(int exceptionCode) {
//        super(exceptionCode);
//    }

//    @Override
//    public String getMessage(int exceptionCode) {
//        String msg;
//        switch(exceptionCode) {
//            case NO_MC_CODE:
//                msg = NO_MC;
//            break;
//            default:
//                msg = "Unknown error";
//            break;
//        }
//        return msg;
//    }
}
