package com.gaminho.oacproject.exception.song;


import com.gaminho.oacproject.exception.CRUDException;

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

