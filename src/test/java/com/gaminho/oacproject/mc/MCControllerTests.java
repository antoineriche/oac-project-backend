package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.exception.mc.InvalidMCException;
import com.gaminho.oacproject.exception.mc.MCNotFoundException;
import com.gaminho.oacproject.exception.mc.NoMCException;
import com.gaminho.oacproject.exception.song.NoSongException;
import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.web.contoller.MCController;
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
public class MCControllerTests {

    @Mock
    private MCService mcService;
    @InjectMocks
    private MCController mcController;

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
        when(mcService.getAllMCs()).thenReturn(list);

        ResponseEntity<?> resp = mcController.getAllMCs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
    }

    @Test
    public void test_getAllMCsWithEmptyDB() throws NoMCException {
        when(mcService.getAllMCs()).thenThrow(new NoMCException());
        ResponseEntity<?> resp = mcController.getAllMCs();

        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No MC.", resp.getBody());
    }

    // GET MC BY ID

    @Test
    public void test_getMCById(){
        when(mcService.getMCWithId(DEFAULT_MC_1.getId())).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.getMCWithId(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }

    @Test
    public void test_getMCByIdWithNotFoundException() throws MCNotFoundException {
        when(mcService.getMCWithId(DEFAULT_MC_1.getId()))
                .thenThrow(new MCNotFoundException(DEFAULT_MC_1.getId()));

        ResponseEntity<?> resp = mcController.getMCWithId(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("MC with id %d does not exist.", DEFAULT_MC_1.getId()),
                resp.getBody());
    }

    // SAVE MC

    @Test
    public void test_saveMC(){
        when(mcService.saveMC(DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.createMC(DEFAULT_MC_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }

    @Test
    public void test_saveMCWithInvalidData() throws InvalidMCException {
        when(mcService.saveMC(DEFAULT_MC_1)).thenThrow(new InvalidMCException());

        ResponseEntity<?> resp = mcController.createMC(DEFAULT_MC_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("Invalid MC.", resp.getBody());
    }

    // UPDATE MC

    @Test
    public void test_updateMC(){
        when(mcService.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }

    @Test
    public void test_updateMCWithNotFoundException() throws MCNotFoundException {
        when(mcService.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1))
                .thenThrow(new MCNotFoundException(DEFAULT_MC_1.getId()));

        ResponseEntity<?> resp = mcController.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("MC with id %d does not exist.", DEFAULT_MC_1.getId()),
                resp.getBody());
    }

    //DELETE MC

    @Test
    public void test_deleteMC(){
        doNothing().when(mcService).deleteMC(DEFAULT_MC_1.getId());
        ResponseEntity<?> resp = mcController.deleteMC(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteMCWithNotFoundException() throws MCNotFoundException {
        doThrow(new MCNotFoundException(DEFAULT_MC_1.getId()))
                .when(mcService).deleteMC(DEFAULT_MC_1.getId());

        ResponseEntity<?> resp = mcController
                .deleteMC(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("MC with id %d does not exist.", DEFAULT_MC_1.getId()),
                resp.getBody());

    }

    // DELETE ALL

    @Test
    public void test_deleteAllMCs() {
        doNothing().when(mcService).deleteAllMCs();
        ResponseEntity<?> resp = mcController.deleteAllMCs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteAllMCsWithNoTypeException() throws NoMCException {
        doThrow(new NoMCException()).when(mcService).deleteAllMCs();
        ResponseEntity<?> resp = mcController.deleteAllMCs();
        assertEquals(HttpStatus.NO_CONTENT.value(), resp.getStatusCodeValue());
    }


}
