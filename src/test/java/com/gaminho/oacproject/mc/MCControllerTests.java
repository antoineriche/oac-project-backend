package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.exception.MCException;
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
import static org.mockito.Mockito.when;

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
    public void testGetAllMCsWithEmptyList() {
        when(mcService.getAllMCs()).thenThrow(new MCException(MCException.NO_MC));
        ResponseEntity<?> resp = mcController.getAllMCs();

        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(MCException.NO_MC, resp.getBody());
    }

    @Test
    public void testGetAllMCs() {
        List<MC> list = new ArrayList<>();
        list.add(DEFAULT_MC_1);
        list.add(DEFAULT_MC_2);
        when(mcService.getAllMCs()).thenReturn(list);
        ResponseEntity<?> resp = mcController.getAllMCs();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
    }

    // GET MC BY ID

    @Test
    public void testGetMCById(){
        when(mcService.getMCWithId(DEFAULT_MC_1.getId())).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.getMCWithId(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }

    @Test
    public void testGetMCByIdNoMCException() throws MCException {
        when(mcService.getMCWithId(DEFAULT_MC_1.getId()))
                .thenThrow(new MCException("No mc found for id " + DEFAULT_MC_1.getId()));

        ResponseEntity<?> resp = mcController.getMCWithId(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No mc found for id " + DEFAULT_MC_1.getId(), resp.getBody());
    }

    // SAVE MC

    @Test
    public void testSavingMC(){
        when(mcService.saveMC(DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.createMC(DEFAULT_MC_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }

    @Test
    public void testSavingMCWithouNameThrowException() throws MCException {
        when(mcService.saveMC(DEFAULT_MC_1)).thenThrow(new MCException(MCException.INVALID_MC));

        ResponseEntity<?> resp = mcController.createMC(DEFAULT_MC_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(MCException.INVALID_MC, resp.getBody());
    }

    // UPDATE MC

    @Test
    public void testUpdatingMC(){
        when(mcService.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1)).thenReturn(DEFAULT_MC_1);

        ResponseEntity<?> resp = mcController.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_MC_1, resp.getBody());
    }


    @Test
    public void testUpdatingMCWithNotFoundMC(){
        when(mcService.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1))
                .thenThrow(new MCException("MC with id " + DEFAULT_MC_1.getId() + " does not exist"));

        ResponseEntity<?> resp = mcController.updateMC(DEFAULT_MC_1.getId(), DEFAULT_MC_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("MC with id " + DEFAULT_MC_1.getId() + " does not exist", resp.getBody());
    }

    //DELETE MC

    @Test
    public void testDeleteMC(){
        when(mcService.deleteMC(DEFAULT_MC_1.getId()))
                .thenReturn("MC with id " + DEFAULT_MC_1.getId() + " does not exist");

        ResponseEntity<?> resp = mcController.deleteMC(DEFAULT_MC_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("MC with id " + DEFAULT_MC_1.getId() + " does not exist", resp.getBody());
    }

}
