package com.gaminho.oacproject.exception;

public class SongException extends OACProjectException {

    public SongException(String message) {
        super(message);
    }

    public static final int NO_SONG_CODE = 1;
    public static final String NO_SONG = "No songs found";

//    public SongException(int exceptionCode) {
//        super(exceptionCode);
//    }

//    @Override
//    public String getMessage(int exceptionCode) {
//        String msg;
//        switch(exceptionCode) {
//            case NO_SONG_CODE:
//                msg = NO_SONG;
//                break;
//            default:
//                msg = "Unknown error";
//                break;
//        }
//        return msg;
//    }
}
