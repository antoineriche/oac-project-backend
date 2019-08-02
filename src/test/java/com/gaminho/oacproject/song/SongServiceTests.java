package com.gaminho.oacproject.song;

import com.gaminho.oacproject.dao.SongRepository;
import com.gaminho.oacproject.exception.song.InvalidSongException;
import com.gaminho.oacproject.exception.song.NoSongException;
import com.gaminho.oacproject.exception.song.SongNotFoundException;
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
import java.util.List;
import java.util.Optional;

import static com.gaminho.oacproject.utils.DefaultValues.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SongServiceTests {

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
		List<Song> list = new ArrayList<>();
		list.add(DEFAULT_SONG_1);
		list.add(DEFAULT_SONG_2);
		when(songRepository.findAll()).thenReturn(list);

		List<Song> list2 = songService.getAllSongs();
		assertNotNull(list2);
		assertEquals(2, list2.size());
		assertEquals(DEFAULT_SONG_1.getTitle(), list2.get(0).getTitle());
	}

	@Test
	public void testGetAllSongsWithVoidList() throws NoSongException {
		expectedEx.expect(NoSongException.class);
		expectedEx.expectMessage("No song.");
		songService.getAllSongs();
	}

	// GET SONG BY ID

	@Test
	public void testGetSongById(){
		when(songRepository.findById(DEFAULT_SONG_1.getId()))
				.thenReturn(Optional.of(DEFAULT_SONG_1));

		assertTrue(songRepository.findById(DEFAULT_SONG_1.getId()).isPresent());
		Song s = songService.getSongWithId(DEFAULT_SONG_1.getId());
		assertEquals(s, DEFAULT_SONG_1);
	}

	@Test
	public void testGetSongByIdNoSongException() throws SongNotFoundException {
		expectedEx.expect(SongNotFoundException.class);
		expectedEx.expectMessage("Song with id 3 does not exist.");
		songService.getSongWithId(3);
	}

	// SAVE SONG

	@Test
	public void testSavingSong(){
		when(songRepository.save(DEFAULT_SONG_1)).thenReturn(DEFAULT_SONG_1);
		Song newSong = songService.saveSong(DEFAULT_SONG_1);
		assertEquals(DEFAULT_SONG_1, newSong);
	}

	@Test
	public void testSavingSongWithoutTitleThrowException() throws InvalidSongException {
		expectedEx.expect(InvalidSongException.class);
		expectedEx.expectMessage("Invalid song.");

		Song song = new Song();
		song.setId(59L);
		when(songRepository.save(song)).thenReturn(song);
		songService.saveSong(song);
	}

	// UPDATE SONG

	@Test
	public void testUpdatingSong(){

		when(songRepository.findById(DEFAULT_SONG_1.getId())).thenReturn(Optional.of(DEFAULT_SONG_1));

		Song songUpdate = new Song();
		songUpdate.setId(DEFAULT_SONG_1.getId());
		songUpdate.setTitle("update");

		when(songRepository.findById(songUpdate.getId())).thenReturn(Optional.of(songUpdate));

		Song s2 = songService.updateSong(songUpdate.getId(), songUpdate);
		assertEquals(s2, songUpdate);
	}

	@Test
	public void testUpdatingSongWithException() throws SongNotFoundException {
		expectedEx.expect(SongNotFoundException.class);
		expectedEx.expectMessage("Song with id 8 does not exist.");
		songService.updateSong(8L, DEFAULT_SONG_1);
	}

	// DELETE SONG

	@Test
	public void testDeleteSongById(){
		when(songRepository.findById(DEFAULT_SONG_1.getId())).thenReturn(Optional.of(DEFAULT_SONG_1));

		String s = songService.deleteSong(DEFAULT_SONG_1.getId());
		verify(songRepository, times(1)).deleteById(DEFAULT_SONG_1.getId());
		assertEquals("Song has been deleted", s);
	}

	@Test
	public void testDeleteSongByIdWithNoSong(){
		String s = songService.deleteSong(8L);
		assertFalse(songRepository.findById(8L).isPresent());
		assertEquals("Song with id 8 does not exist", s);
	}

	// DELETE ALL

	@Test
	public void testDeleteAllSongsWithEmptyList() {
		String s = songService.deleteAllSongs();
		assertEquals(s, "No song in database");
	}

	@Test
	public void testDeleteAllSongs() {
		List<Song> list = new ArrayList<>();
		list.add(DEFAULT_SONG_1);
		list.add(DEFAULT_SONG_2);

		when(songRepository.count()).thenReturn(Long.valueOf(list.size()));

		String s = songService.deleteAllSongs();
		assertEquals(s,
				String.format("%d songs have been deleted", list.size()));
	}

}
