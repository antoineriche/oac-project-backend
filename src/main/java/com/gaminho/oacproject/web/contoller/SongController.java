package com.gaminho.oacproject.web.contoller;

import com.gaminho.oacproject.error.exception.song.InvalidSongException;
import com.gaminho.oacproject.error.exception.song.NoSongException;
import com.gaminho.oacproject.error.exception.song.SongNotFoundException;
import com.gaminho.oacproject.model.Song;
import com.gaminho.oacproject.web.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * GET /songs : get all songs
     * @return the ResponseEntity with status 200 (OK) and the list of {@link Song} or a message of empty data
     */
    @GetMapping(value = "/songs")
    public ResponseEntity<?> getAllSongs(){
        try {
            List<Song> list = songService.getAllSongs();
            return ResponseEntity.ok(list);
        } catch (NoSongException e){
//            return ResponseEntity.noContent().build();
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * GET  /songs/:id : get the "id" song.
     *
     * @param id the id of the song to retrieve
     * @return the ResponseEntity with status 200 (OK) with song as body or with status 404 (Not Found)
     */
    @GetMapping(value="/songs/{id}")
    public ResponseEntity<?> getSongWithId(@PathVariable(value = "id") long id) {
        try {
            return ResponseEntity.ok(songService.getSongWithId(id));
        } catch (SongNotFoundException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * POST /songs : create a new song
     * @param song song to save
     * @return ResponseEntity with status 201 (created) with song as body or a status 400 (Bad request)
     */
    @PostMapping(value = "/songs")
    public ResponseEntity<?> createSong(@Valid @RequestBody Song song){
        try {
            Song newSong = songService.saveSong(song);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newSong.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newSong);
        } catch (InvalidSongException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /songs/:id : update the song with given id
     * @param id id of the song to update
     * @param song song with new data
     * @return ResponseEntity with status 201 (created) with song as body or a status 400 (Bad request)
     */
    @PutMapping(value = "/songs/{id}")
    public ResponseEntity<?> updateSong(@PathVariable(value = "id") long id, @Valid @RequestBody Song song){
        try {
            Song newSong = songService.updateSong(id, song);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).body(newSong);
        } catch (SongNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /songs/:id : delete the song with given id
     * @param id id of the song to delete
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    //TODO verify negative id or null id
    @DeleteMapping(value="/songs/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable(value = "id") long id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.ok().build();
        } catch (SongNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /songs : delete all songs
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    @DeleteMapping(value="/songs")
    public ResponseEntity<?> deleteAllSongs() {
        try {
            songService.deleteAllSongs();
            return ResponseEntity.ok().build();
        } catch (NoSongException e) {
            return ResponseEntity.noContent().build();
        }
    }


}
