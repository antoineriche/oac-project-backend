package com.gaminho.oacproject.web.service;

import com.gaminho.oacproject.dao.SongRepository;
import com.gaminho.oacproject.exception.song.InvalidSongException;
import com.gaminho.oacproject.exception.song.NoSongException;
import com.gaminho.oacproject.exception.song.SongNotFoundException;
import com.gaminho.oacproject.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SongService {

    @Autowired
    private SongRepository songRepository;

    //FIXME make a property file with all Strings
    public Song getSongWithId(long id) {
        Optional<Song> song = songRepository.findById(id);
        if (!song.isPresent()) {
            throw new SongNotFoundException(id);
        }
        return song.get();
    }

    public List<Song> getAllSongs(){
        List<Song> songs = songRepository.findAll();
        if(songs.isEmpty()) {
            throw new NoSongException();
        } else {
            return songs;
        }
    }

    @Transactional
    public Song saveSong(Song songToSave){
        if(Song.isValid(songToSave)) {
            songToSave.setCreationDate(new Date());
            // TODO what to do if there's already an id ?
            return songRepository.save(songToSave);
        } else {
            throw new InvalidSongException();
        }
    }

    @Transactional
    public Song updateSong(long id, Song updatedSong){
        Optional<Song> oldSong = songRepository.findById(id);

        if(oldSong.isPresent()) {
            Song song = oldSong.get();
            song.setTitle(updatedSong.getTitle());
            songRepository.flush();
            return song;
        } else {
            throw new SongNotFoundException(id);
        }
    }

    @Transactional
    //FIXME try to parse msg as JSON but it's string
    public String deleteSong(long id){
        Optional<Song> song = songRepository.findById(id);
        if(song.isPresent()) {
            songRepository.deleteById(id);
            return "Song has been deleted";
        } else {
            return "Song with id " + id + " does not exist";
        }
    }

    //TODO test
    @Transactional
    public String deleteAllSongs(){
        long count = songRepository.count();
        if(count > 0) {
            songRepository.deleteAll();
            return String.format("%d songs have been deleted", count);
        } else {
            return "No song in database";
        }
    }
}
