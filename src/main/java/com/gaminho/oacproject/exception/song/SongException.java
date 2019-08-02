package com.gaminho.oacproject.exception.song;

import com.gaminho.oacproject.exception.OACProjectException;

public abstract class SongException extends OACProjectException {

    public static final int SONG_NOT_FOUND_CODE = 1;
    public static final int INVALID_SONG_CODE = 2;
    public static final int NO_SONG_CODE = 3;

    public SongException(int exceptionCode) {
            super(exceptionCode);
    }

}
