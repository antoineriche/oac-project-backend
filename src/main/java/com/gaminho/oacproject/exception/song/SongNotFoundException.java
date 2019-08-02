package com.gaminho.oacproject.exception.song;


public class SongNotFoundException extends SongException {

    private long idSong;

    public SongNotFoundException(long pISong) {
        super(SongException.SONG_NOT_FOUND_CODE);
        idSong = pISong;
    }

    @Override
    public String getExceptionMessage() {
        return String.format("Song with id %d does not exist.", this.idSong);
    }


}

