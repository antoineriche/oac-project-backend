package com.gaminho.oacproject.song;

import com.gaminho.oacproject.exception.song.InvalidSongException;
import com.gaminho.oacproject.exception.song.NoSongException;
import com.gaminho.oacproject.exception.song.SongNotFoundException;
import com.gaminho.oacproject.model.Song;
import com.gaminho.oacproject.web.contoller.SongController;
import com.gaminho.oacproject.web.service.SongService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static com.gaminho.oacproject.utils.DefaultValues.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SongControllerTests {


    @Mock
    private SongService songService;
    @InjectMocks
    private SongController songController;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // GET ALL SONGS

    @Test
    public void test_getAllSongs() {
        List<Song> list = new ArrayList<>();
        list.add(DEFAULT_SONG_1);
        list.add(DEFAULT_SONG_2);
        when(songService.getAllSongs()).thenReturn(list);
        ResponseEntity<?> resp = songController.getAllSongs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
    }

    @Test
    public void test_getAllSongsWithEmptyDB() throws NoSongException {
        when(songService.getAllSongs()).thenThrow(new NoSongException());
        ResponseEntity<?> resp = songController.getAllSongs();

        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No song.", resp.getBody());
    }

    // GET SONG BY ID

    @Test
    public void test_getSongById(){
        when(songService.getSongWithId(DEFAULT_SONG_1.getId())).thenReturn(DEFAULT_SONG_1);

        ResponseEntity<?> resp = songController.getSongWithId(DEFAULT_SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_SONG_1, resp.getBody());
    }

    @Test
    public void test_getSongByIdWithNotFoundException() throws SongNotFoundException {
        when(songService.getSongWithId(DEFAULT_SONG_1.getId()))
                .thenThrow(new SongNotFoundException(DEFAULT_SONG_1.getId()));

        ResponseEntity<?> resp = songController.getSongWithId(DEFAULT_SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Song with id %d does not exist.", DEFAULT_SONG_1.getId()),
                resp.getBody());
    }

    // SAVE SONG

    @Test
    public void test_saveSong(){
        when(songService.saveSong(DEFAULT_SONG_1)).thenReturn(DEFAULT_SONG_1);

        ResponseEntity<?> resp = songController.createSong(DEFAULT_SONG_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_SONG_1, resp.getBody());
    }

    @Test
    public void test_saveSongWithInvalidData() throws InvalidSongException {
        when(songService.saveSong(DEFAULT_SONG_1)).thenThrow(new InvalidSongException());

        ResponseEntity<?> resp = songController.createSong(DEFAULT_SONG_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("Invalid song.", resp.getBody());
    }

    // UPDATE SONG

    @Test
    public void test_updateSong(){
        when(songService.updateSong(DEFAULT_SONG_1.getId(), DEFAULT_SONG_1))
                .thenReturn(DEFAULT_SONG_1);

        ResponseEntity<?> resp = songController.updateSong(DEFAULT_SONG_1.getId(), DEFAULT_SONG_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_SONG_1, resp.getBody());
    }

    @Test
    public void test_updateSongWithNotFoundException() throws SongNotFoundException {
        when(songService.updateSong(DEFAULT_SONG_1.getId(), DEFAULT_SONG_1))
                .thenThrow(new SongNotFoundException(DEFAULT_SONG_1.getId()));

        ResponseEntity<?> resp = songController.updateSong(DEFAULT_SONG_1.getId(), DEFAULT_SONG_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Song with id %d does not exist.", DEFAULT_SONG_1.getId()),
                resp.getBody());
    }

    //DELETE SONG

    @Test
    public void testDeleteSong(){
        doNothing().when(songService).deleteSong(DEFAULT_SONG_1.getId());
        ResponseEntity<?> resp = songController.deleteSong(DEFAULT_SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteSongWithNotFoundException() throws SongNotFoundException {
        doThrow(new SongNotFoundException(DEFAULT_SONG_1.getId()))
                .when(songService).deleteSong(DEFAULT_SONG_1.getId());

        ResponseEntity<?> resp = songController
                .deleteSong(DEFAULT_SONG_1.getId());
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Song with id %d does not exist.", DEFAULT_SONG_1.getId()),
                resp.getBody());
    }

    // DELETE ALL

    @Test
    public void test_deleteAllSongs() {
        doNothing().when(songService).deleteAllSongs();
        ResponseEntity<?> resp = songController.deleteAllSongs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteAllSongsWithNoTypeException() throws NoSongException {
        doThrow(new NoSongException()).when(songService).deleteAllSongs();

        ResponseEntity<?> resp = songController.deleteAllSongs();
        assertEquals(HttpStatus.NO_CONTENT.value(), resp.getStatusCodeValue());
    }

}
