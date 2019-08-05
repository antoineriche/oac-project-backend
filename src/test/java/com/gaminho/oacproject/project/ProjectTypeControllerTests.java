package com.gaminho.oacproject.project;

import com.gaminho.oacproject.error.OACApiError;
import com.gaminho.oacproject.error.OACApiErrorTests;
import com.gaminho.oacproject.error.exception.project.InvalidTypeException;
import com.gaminho.oacproject.error.exception.project.NoTypeException;
import com.gaminho.oacproject.error.exception.project.TypeNotFoundException;
import com.gaminho.oacproject.model.ProjectType;
import com.gaminho.oacproject.web.contoller.ProjectTypeController;
import com.gaminho.oacproject.web.service.ProjectTypeService;
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
public class ProjectTypeControllerTests {

    @Mock
    private ProjectTypeService typeService;
    @InjectMocks
    private ProjectTypeController typeController;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // GET ALL TYPES

    @Test
    public void test_getAllTypes() {
        List<ProjectType> list = new ArrayList<>();
        list.add(DEFAULT_PROJECT_TYPE_1);
        list.add(DEFAULT_PROJECT_TYPE_2);
        when(typeService.getAllProjectTypes()).thenReturn(list);

        ResponseEntity<?> resp = typeController.getAllProjectTypes();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
    }

    @Test
    public void test_getAllTypesWithEmptyDB() throws NoTypeException {
        when(typeService.getAllProjectTypes()).thenThrow(new NoTypeException());
        ResponseEntity<?> resp = typeController.getAllProjectTypes();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals("No project type.", resp.getBody());
    }

    // GET TYPE BY ID

    @Test
    public void test_getTypeById(){
        when(typeService.getProjectTypeWithId(DEFAULT_PROJECT_TYPE_1.getId()))
                .thenReturn(DEFAULT_PROJECT_TYPE_1);

        ResponseEntity<?> resp = typeController.getProjectTypeWithId(DEFAULT_PROJECT_TYPE_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_PROJECT_TYPE_1, resp.getBody());
    }

    @Test
    public void test_getTypeByIdWithNotFoundException() throws TypeNotFoundException {
        when(typeService.getProjectTypeWithId(DEFAULT_PROJECT_TYPE_1.getId()))
                .thenThrow(new TypeNotFoundException(DEFAULT_PROJECT_TYPE_1.getId()));

        ResponseEntity<?> resp = typeController.getProjectTypeWithId(DEFAULT_PROJECT_TYPE_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Project type with id %d does not exist.", DEFAULT_PROJECT_TYPE_1.getId()),
                resp.getBody());
    }

    // SAVE TYPE

    @Test
    public void test_saveType(){
        when(typeService.saveProjectType(DEFAULT_PROJECT_TYPE_1))
                .thenReturn(DEFAULT_PROJECT_TYPE_1);

        ResponseEntity<?> resp = typeController.createProjectType(DEFAULT_PROJECT_TYPE_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_PROJECT_TYPE_1, resp.getBody());
    }

    @Test
    public void test_saveTypeWithInvalidData() throws InvalidTypeException {
        when(typeService.saveProjectType(DEFAULT_PROJECT_TYPE_1))
                .thenThrow(new InvalidTypeException());

        typeController.createProjectType(DEFAULT_PROJECT_TYPE_1);


        ResponseEntity<?> resp = typeController.createProjectType(DEFAULT_PROJECT_TYPE_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());

        OACApiError error = new OACApiError(
                "InvalidTypeException",
                HttpStatus.BAD_REQUEST,
                "Invalid project type.");

        assertEquals("Invalid project type.", error.getErrorDetail());
    }

    // UPDATE TYPE

    @Test
    public void test_updateType(){
        when(typeService.updateProjectType(DEFAULT_PROJECT_TYPE_1.getId(), DEFAULT_PROJECT_TYPE_1))
                .thenReturn(DEFAULT_PROJECT_TYPE_1);

        ResponseEntity<?> resp = typeController
                .updateProjectType(DEFAULT_PROJECT_TYPE_1.getId(), DEFAULT_PROJECT_TYPE_1);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCodeValue());
        assertEquals(DEFAULT_PROJECT_TYPE_1, resp.getBody());
    }

    @Test
    public void test_updateTypeWithNotFoundException() throws TypeNotFoundException {
        when(typeService.updateProjectType(DEFAULT_PROJECT_TYPE_1.getId(), DEFAULT_PROJECT_TYPE_1))
                .thenThrow(new TypeNotFoundException(DEFAULT_PROJECT_TYPE_1.getId()));

        ResponseEntity<?> resp = typeController
                .updateProjectType(DEFAULT_PROJECT_TYPE_1.getId(), DEFAULT_PROJECT_TYPE_1);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Project type with id %d does not exist.", DEFAULT_PROJECT_TYPE_1.getId()),
                resp.getBody());
    }

    // DELETE TYPE

    @Test
    public void test_deleteType(){
        doNothing().when(typeService).deleteProjectType(DEFAULT_PROJECT_TYPE_1.getId());
        ResponseEntity<?> resp = typeController.deleteProjectType(DEFAULT_PROJECT_TYPE_1.getId());
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteTypeWithNotFoundException() throws TypeNotFoundException {
        doThrow(new TypeNotFoundException(DEFAULT_PROJECT_TYPE_1.getId()))
                .when(typeService).deleteProjectType(DEFAULT_PROJECT_TYPE_1.getId());

        ResponseEntity<?> resp = typeController
                .deleteProjectType(DEFAULT_PROJECT_TYPE_1.getId());
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(
                String.format("Project type with id %d does not exist.", DEFAULT_PROJECT_TYPE_1.getId()),
                resp.getBody());

    }

    // DELETE ALL

    @Test
    public void test_deleteAllTypes() {
        doNothing().when(typeService).deleteAllTypes();
        ResponseEntity<?> resp = typeController.deleteAllProjectTypes();
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
    }

    @Test
    public void test_deleteAllTypesWithNoTypeException() throws NoTypeException {
        doThrow(new NoTypeException()).when(typeService).deleteAllTypes();

        ResponseEntity<?> resp = typeController.deleteAllProjectTypes();
        assertEquals(HttpStatus.NO_CONTENT.value(), resp.getStatusCodeValue());
    }

}
