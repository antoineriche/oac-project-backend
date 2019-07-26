package com.gaminho.oacproject.web.contoller;

import com.gaminho.oacproject.dao.SongDao;
import com.gaminho.oacproject.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class SongController {

    @Autowired
    private SongDao songDao;

    @GetMapping(value = "/songs")
    public ResponseEntity<List<Song>> getAllSongs(){
        List<Song> songs = songDao.findAll();
        if(songs != null) {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/songs/{id}")
    public ResponseEntity<Song> getSongWithId(@PathVariable(value = "id") long id) {
        Song song = songDao.findById(id);
        if(song != null) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/songs")
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song){
        song.setCreationDate(new Date());
        Song newSong = songDao.save(song);

        if (newSong == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newSong.getId())
                    .toUri();

            return ResponseEntity.created(location).body(newSong);
        }
    }

}
