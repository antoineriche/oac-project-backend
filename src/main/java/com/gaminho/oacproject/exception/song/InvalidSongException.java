package com.gaminho.oacproject.exception.song;

import com.gaminho.oacproject.exception.CRUDException;

public class InvalidSongException extends CRUDException {

    public InvalidSongException() {
        super(CRUDException.INVALID_DATA_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return invalidDataMessage("song");
    }

}
