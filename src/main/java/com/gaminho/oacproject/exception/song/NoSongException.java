package com.gaminho.oacproject.exception.song;

public class NoSongException extends SongException {

    public NoSongException() {
        super(SongException.NO_SONG_CODE);
    }

    @Override
    protected String getExceptionMessage() {
        return "No song.";
    }
}
