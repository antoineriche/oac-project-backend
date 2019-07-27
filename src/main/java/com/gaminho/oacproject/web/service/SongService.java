package com.gaminho.oacproject.web.service;

import com.gaminho.oacproject.dao.SongRepository;
import com.gaminho.oacproject.exception.SongException;
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
            throw new SongException("No song found for id " + id);
        }
        return song.get();
    }

    public List<Song> getAllSongs(){
        List<Song> songs = songRepository.findAll();
        if(songs.isEmpty()) {
            throw new SongException("No songs");
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
            throw new SongException("Invalid song");
        }
    }

    @Transactional
    public String deleteSong(long id){
        Optional<Song> song = songRepository.findById(id);
        if(song.isPresent()) {
            songRepository.deleteById(id);
            return "Song has been deleted";
        } else {
            return "Song with id " + id + " does not exist";
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
            throw new SongException("Song with id " + id + " does not exist");
        }
    }
}
