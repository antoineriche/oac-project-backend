package com.gaminho.oacproject.error.exception.song;


import com.gaminho.oacproject.error.exception.CRUDException;

public class SongNotFoundException extends CRUDException {

    private long idSong;

    public SongNotFoundException(long pISong) {
        super(CRUDException.DATA_NOT_FOUND_CODE);
        idSong = pISong;
    }

    @Override
    public String getExceptionMessage() {
        return dataNotFoundMessage("Song", this.idSong);
    }

}

