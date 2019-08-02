package com.gaminho.oacproject.exception.song;

public class InvalidSongException extends SongException {

    public InvalidSongException() {
        super(SongException.INVALID_SONG_CODE);
    }

    @Override
    public String getExceptionMessage() {
        return "Invalid song.";
    }

}
