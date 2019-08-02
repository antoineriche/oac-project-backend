package com.gaminho.oacproject.exception.song;

import com.gaminho.oacproject.exception.CRUDException;

public class NoSongException extends CRUDException
{

    public NoSongException() {
        super(CRUDException.NO_DATA_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return noDataMessage("song");
    }
}
