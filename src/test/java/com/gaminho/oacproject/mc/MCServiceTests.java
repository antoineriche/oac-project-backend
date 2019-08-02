package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.dao.MCRepository;
import com.gaminho.oacproject.exception.mc.InvalidMCException;
import com.gaminho.oacproject.exception.mc.MCNotFoundException;
import com.gaminho.oacproject.exception.mc.NoMCException;
import com.gaminho.oacproject.model.MC;
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
	public void test_getAllMCs() {
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
	public void test_getAllMCsWithEmptyDB() throws NoMCException {
		expectedEx.expect(NoMCException.class);
		expectedEx.expectMessage("No MC.");
		mcService.getAllMCs();
	}

	// GET MC BY ID

	@Test
	public void test_getMCById(){
		when(mcRepository.findById(DEFAULT_MC_1.getId()))
				.thenReturn(Optional.of(DEFAULT_MC_1));

		assertTrue(mcRepository.findById(DEFAULT_MC_1.getId()).isPresent());
		MC mc = mcService.getMCWithId(DEFAULT_MC_1.getId());
		assertEquals(mc, DEFAULT_MC_1);
	}

	@Test
	public void test_getMCByIdWithNotFoundException() throws MCNotFoundException {
		expectedEx.expect(MCNotFoundException.class);
		expectedEx.expectMessage("MC with id 3 does not exist.");
		mcService.getMCWithId(3);
	}

	// SAVE MC

	@Test
	public void test_saveMC(){
		when(mcRepository.save(DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);
		MC newMC = mcService.saveMC(DEFAULT_MC_1);
		assertEquals(DEFAULT_MC_1, newMC);
	}

	@Test
	public void test_saveMCWithInvalidData() throws InvalidMCException {
		expectedEx.expect(InvalidMCException.class);
		expectedEx.expectMessage("Invalid MC.");

		MC mc = new MC();
		mc.setId(59L);
		when(mcRepository.save(mc)).thenReturn(mc);
		mcService.saveMC(mc);
	}

	// UPDATE MC

	@Test
	public void test_updateMC(){

		when(mcRepository.findById(DEFAULT_MC_1.getId())).thenReturn(Optional.of(DEFAULT_MC_1));

		MC mcUpdate = new MC();
		mcUpdate.setId(DEFAULT_MC_1.getId());
		mcUpdate.setName("update");

		when(mcRepository.findById(mcUpdate.getId())).thenReturn(Optional.of(mcUpdate));

		MC mc2 = mcService.updateMC(mcUpdate.getId(), mcUpdate);
		assertEquals(mc2, mcUpdate);
	}

	@Test
	public void test_updateMCWithNotFoundException() throws MCNotFoundException {
		expectedEx.expect(MCNotFoundException.class);
		expectedEx.expectMessage("MC with id 8 does not exist.");
		mcService.updateMC(8L, DEFAULT_MC_1);
	}

	// DELETE MC

	@Test
	public void test_deleteMC(){
		when(mcRepository.findById(DEFAULT_MC_1.getId()))
				.thenReturn(Optional.of(DEFAULT_MC_1));

		mcService.deleteMC(DEFAULT_MC_1.getId());
		verify(mcRepository, times(1)).deleteById(DEFAULT_MC_1.getId());
	}

	@Test
	public void test_deleteMCByIdWithNotFoundException() throws MCNotFoundException {
		expectedEx.expect(MCNotFoundException.class);
		expectedEx.expectMessage("MC with id 8 does not exist.");
		mcService.deleteMC(8L);
	}

	// DELETE ALL

	@Test
	public void test_deleteAllMCs() {
		when(mcRepository.count()).thenReturn(3L);
		mcService.deleteAllMCs();
		verify(mcRepository, times(1)).deleteAll();
	}

	@Test
	public void test_deleteAllMCsWithEmptyList() throws NoMCException {
		expectedEx.expect(NoMCException.class);
		expectedEx.expectMessage("No MC.");
		when(mcRepository.count()).thenReturn(0L);
		mcService.deleteAllMCs();
	}

}
