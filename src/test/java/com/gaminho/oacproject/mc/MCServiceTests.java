package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.dao.MCRepository;
import com.gaminho.oacproject.exception.MCException;
import com.gaminho.oacproject.exception.SongException;
import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.model.Song;
import com.gaminho.oacproject.web.service.MCService;
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
import static com.gaminho.oacproject.utils.DefaultValues.DEFAULT_SONG_2;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MCServiceTests {

	@Mock
	private MCRepository mcRepository;
	@InjectMocks
	private MCService mcService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}


	// GET ALL MCs

	@Test
	public void testGetAllSongs() {
		List<MC> list = new ArrayList<>();
		list.add(DEFAULT_MC_1);
		list.add(DEFAULT_MC_2);
		when(mcRepository.findAll()).thenReturn(list);

		List<MC> list2 = mcService.getAllMCs();
		assertNotNull(list2);
		assertEquals(2, list2.size());
		assertEquals(DEFAULT_MC_1.getName(), list2.get(0).getName());
	}

	@Test
	public void testGetAllSongsWithVoidList() throws MCException {
		expectedEx.expect(MCException.class);
		expectedEx.expectMessage(MCException.NO_MC);
		mcService.getAllMCs();
	}

	// GET MC BY ID

	@Test
	public void testGetMCById(){
		when(mcRepository.findById(DEFAULT_MC_1.getId()))
				.thenReturn(Optional.of(DEFAULT_MC_1));

		assertTrue(mcRepository.findById(DEFAULT_MC_1.getId()).isPresent());
		MC mc = mcService.getMCWithId(DEFAULT_MC_1.getId());
		assertEquals(mc, DEFAULT_MC_1);
	}

	@Test
	public void testGetMCByIdNoSongException() throws MCException {
		expectedEx.expect(MCException.class);
		expectedEx.expectMessage("No mc found for id 3");
		mcService.getMCWithId(3);
	}

	// SAVE MC

	@Test
	public void testSavingMC(){
		when(mcRepository.save(DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);
		MC newMC = mcService.saveMC(DEFAULT_MC_1);
		assertEquals(DEFAULT_MC_1, newMC);
	}

	@Test
	public void testSavingMCWithoutNameThrowException() throws MCException {
		expectedEx.expect(MCException.class);
		expectedEx.expectMessage(MCException.INVALID_MC);

		MC mc = new MC();
		mc.setId(59L);
		when(mcRepository.save(mc)).thenReturn(mc);
		mcService.saveMC(mc);
	}

	// UPDATE MC

	@Test
	public void testUpdatingMC(){

		when(mcRepository.findById(DEFAULT_MC_1.getId())).thenReturn(Optional.of(DEFAULT_MC_1));

		MC mcUpdate = new MC();
		mcUpdate.setId(DEFAULT_MC_1.getId());
		mcUpdate.setName("update");

		when(mcRepository.findById(mcUpdate.getId())).thenReturn(Optional.of(mcUpdate));

		MC mc2 = mcService.updateMC(mcUpdate.getId(), mcUpdate);
		assertEquals(mc2, mcUpdate);
	}

	@Test
	public void testUpdatingSongWithException() throws MCException {
		expectedEx.expect(MCException.class);
		expectedEx.expectMessage("MC with id 8 does not exist");
		mcService.updateMC(8L, DEFAULT_MC_1);
	}

	// DELETE MC

	@Test
	public void testDeleteMCById(){
		when(mcRepository.findById(DEFAULT_MC_1.getId())).thenReturn(Optional.of(DEFAULT_MC_1));

		String s = mcService.deleteMC(DEFAULT_MC_1.getId());
		verify(mcRepository, times(1)).deleteById(DEFAULT_MC_1.getId());
		assertEquals("MC has been deleted", s);
	}

	@Test
	public void testDeleteMCByIdWithNoMC(){
		String s = mcService.deleteMC(8L);
		assertFalse(mcRepository.findById(8L).isPresent());
		assertEquals("MC with id 8 does not exist", s);
	}

	// DELETE ALL

	@Test
	public void testDeleteAllMCsWithEmptyList() {
		String s = mcService.deleteAllMCs();
		assertEquals(s, "No mc in database");
	}

	@Test
	public void testDeleteAllMCs() {
		List<MC> list = new ArrayList<>();
		list.add(DEFAULT_MC_1);
		list.add(DEFAULT_MC_2);
		when(mcRepository.count()).thenReturn(Long.valueOf(list.size()));

		String s = mcService.deleteAllMCs();
		assertEquals(s,
				String.format("%d mcs have been deleted", list.size()));
	}

}
