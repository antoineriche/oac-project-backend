package com.gaminho.oacproject.error.exception.song;

import com.gaminho.oacproject.error.exception.CRUDException;

public class InvalidSongException extends CRUDException {

    public InvalidSongException() {
        super(CRUDException.INVALID_DATA_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return invalidDataMessage("song");
    }

}
