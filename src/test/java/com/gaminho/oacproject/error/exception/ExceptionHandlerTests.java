package com.gaminho.oacproject.error.exception;

import com.gaminho.oacproject.dao.ProjectTypeRepository;
import com.gaminho.oacproject.error.OACApiError;
import com.gaminho.oacproject.error.OACApiErrorTests;
import com.gaminho.oacproject.model.ProjectType;
import com.gaminho.oacproject.web.OACExceptionHandler;
import com.gaminho.oacproject.web.contoller.ProjectTypeController;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionHandlerTests {

    private MockMvc mvc;

    @Mock
    private ProjectTypeController typeController;

    @Mock
    private ProjectTypeRepository typeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(typeController)
                .setControllerAdvice(new OACExceptionHandler())
                .build();
    }

    @Test
    public void test_1() throws Exception {

        // given
        when(typeRepository.save(new ProjectType()))
                .thenThrow(new ConstraintViolationException("a", new SQLException(), "b"));

        // when

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/project-types", new ProjectType())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        System.out.println(response.getErrorMessage());
        System.out.println(response.getStatus());

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testHandleConstraintViolation(){
        ConstraintViolationException exception =
                new ConstraintViolationException("message", new SQLException("casquette"), "constraintName");
        ResponseEntity<OACApiError> res = OACExceptionHandler.handleConstraintViolation(exception);

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST, res.getBody().getStatus());

        OACApiError error = new OACApiError(
                "ConstraintViolationException",
                HttpStatus.BAD_REQUEST,
                "casquette");

        assertEquals(error, res.getBody());

    }

}
