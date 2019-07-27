package com.gaminho.oacproject;

import com.gaminho.oacproject.dao.SongRepository;
import com.gaminho.oacproject.exception.SongException;
import com.gaminho.oacproject.model.Song;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OacprojectApplicationTests {

	private static Song SONG_1 = new Song(1L, "SONG1", new Date());
	private static Song SONG_2 = new Song(2L, "SONG2", new Date());

	@Mock
	private SongRepository songRepository;
	@InjectMocks
	private SongService songService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}


	// GET ALL SONGS

	@Test
	public void testGetAllSongs() {
		List<Song> list = initList();
		when(songRepository.findAll()).thenReturn(list);

		List<Song> list2 = songService.getAllSongs();
		assertNotNull(list2);
		assertEquals(2, list2.size());
		assertEquals(SONG_1.getTitle(), list2.get(0).getTitle());
	}

	@Test
	public void testGetAllSongsWithVoidList() throws SongException {
		expectedEx.expect(SongException.class);
		expectedEx.expectMessage("No songs");
		songService.getAllSongs();
	}

	// GET SONG BY ID

	@Test
	public void testGetSongById(){
		Song song = new Song();
		song.setId(4L);
		song.setTitle("4L");
		when(songRepository.findById(4L)).thenReturn(Optional.of(song));
		assertTrue(songRepository.findById(song.getId()).isPresent());
		Song s = songService.getSongWithId(song.getId());
		assertEquals(s, song);
	}

	@Test
	public void testGetSongByIdNoSongException() throws SongException {
		expectedEx.expect(SongException.class);
		expectedEx.expectMessage("No song found for id 3");
		songService.getSongWithId(3);
	}

	// DELETE SONG
	@Test
	public void testDeleteSongById(){

		Song song = new Song();
		song.setId(8L);
		song.setTitle("te");
		when(songRepository.findById(8L)).thenReturn(Optional.of(song));
		assertTrue(songRepository.findById(8L).isPresent());

		String ss = songService.deleteSong(8L);
		verify(songRepository, times(1)).deleteById(8L);
		assertEquals("Song has been deleted", ss);
	}

	@Test
	public void testDeleteSongByIdWithNoSong(){
		String ss = songService.deleteSong(8L);
		assertFalse(songRepository.findById(8L).isPresent());
		assertEquals("Song with id 8 does not exist", ss);
	}

	@Test
	public void testSavingSong(){
		Song song = new Song();
		song.setId(59L);
		song.setTitle("cas");

		when(songRepository.save(song)).thenReturn(song);
		Song newSong = songService.saveSong(song);
		assertEquals(song, newSong);
	}

	@Test
	public void testSavingSongWithoutTitleThrowException() throws SongException {
		expectedEx.expect(SongException.class);
		expectedEx.expectMessage("Invalid song");

		Song song = new Song();
		song.setId(59L);
		when(songRepository.save(song)).thenReturn(song);
		songService.saveSong(song);
	}

	@Test
	public void testUpdatingSong(){

		Song song = new Song();
		song.setId(1L);
		song.setTitle("init");

		when(songRepository.findById(1L)).thenReturn(Optional.of(song));

		Song songUpdate = new Song();
		songUpdate.setId(song.getId());
		songUpdate.setTitle("update");

		when(songRepository.findById(song.getId())).thenReturn(Optional.of(songUpdate));

		Song s2 = songService.updateSong(song.getId(), songUpdate);
		assertEquals(s2.getId(), songUpdate.getId());
		assertEquals(s2.getTitle(), songUpdate.getTitle());
	}

	@Test
	public void testUpdatingSongWithException() throws SongException {
		expectedEx.expect(SongException.class);
		expectedEx.expectMessage("Song with id 8 does not exist");

		Song song = new Song();
		song.setId(1L);
		song.setTitle("init");

		when(songRepository.findById(1L)).thenReturn(Optional.of(song));
		songService.updateSong(8L, song);
	}

	private static List<Song> initList(){
		List<Song> l = new ArrayList<>();
		l.add(SONG_1);
		l.add(SONG_2);
		return l;
	}

}
