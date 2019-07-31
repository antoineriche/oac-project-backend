package com.gaminho.oacproject;

import com.gaminho.oacproject.exception.SongException;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SongControllerTests {

    private static Song SONG_1 = new Song(1L, "SONG1", new Date());
    private static Song SONG_2 = new Song(2L, "SONG2", new Date());

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
    public void testGetAllSongsWithEmptyList() {
        when(songService.getAllSongs()).thenThrow(new SongException("No songs"));
        ResponseEntity<?> resp = songController.getAllSongs();

        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No songs", resp.getBody());
    }

    @Test
    public void testGetAllSongs() {
        List<Song> list = new ArrayList<>();
        list.add(SONG_1);
        list.add(SONG_2);
        when(songService.getAllSongs()).thenReturn(list);
        ResponseEntity<?> resp = songController.getAllSongs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
    }

    // GET SONG BY ID

    @Test
    public void testGetSongById(){
        when(songService.getSongWithId(SONG_1.getId())).thenReturn(SONG_1);

        ResponseEntity<?> resp = songController.getSongWithId(SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(SONG_1, resp.getBody());
    }

    @Test
    public void testGetSongByIdNoSongException() throws SongException {
        when(songService.getSongWithId(SONG_1.getId()))
                .thenThrow(new SongException("No song found for id " + SONG_1.getId()));

        ResponseEntity<?> resp = songController.getSongWithId(SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No song found for id " + SONG_1.getId(), resp.getBody());
    }


    // SAVE SONG

    @Test
    public void testSavingSong(){
        when(songService.saveSong(SONG_1)).thenReturn(SONG_1);

        ResponseEntity<?> resp = songController.createSong(SONG_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(SONG_1, resp.getBody());
    }

    @Test
    public void testSavingSongWithoutTitleThrowException() throws SongException {
        when(songService.saveSong(SONG_1)).thenThrow(new SongException("Invalid song"));

        ResponseEntity<?> resp = songController.createSong(SONG_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("Invalid song", resp.getBody());
    }

    // UPDATE SONG

    @Test
    public void testUpdatingSong(){
        when(songService.updateSong(SONG_1.getId(), SONG_1)).thenReturn(SONG_1);

        ResponseEntity<?> resp = songController.updateSong(SONG_1.getId(), SONG_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(SONG_1, resp.getBody());
    }

    @Test
    public void testUpdatingSongWithNotFoundSong(){
        when(songService.updateSong(SONG_1.getId(), SONG_1))
                .thenThrow(new SongException("Song with id " + SONG_1.getId() + " does not exist"));

        ResponseEntity<?> resp = songController.updateSong(SONG_1.getId(), SONG_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("Song with id " + SONG_1.getId() + " does not exist", resp.getBody());
    }

    //DELETE SONG
    @Test
    public void testDeleteSong(){
        when(songService.deleteSong(SONG_1.getId()))
                .thenReturn("Song with id " + SONG_1.getId() + " does not exist");

        ResponseEntity<?> resp = songController.deleteSong(SONG_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("Song with id " + SONG_1.getId() + " does not exist", resp.getBody());
    }

}
