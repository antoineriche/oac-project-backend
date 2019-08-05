package com.gaminho.oacproject.error.exception;

import com.gaminho.oacproject.error.exception.mc.InvalidMCException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExceptionTest {

    @Test
    public void testGetExceptionCode() {
        InvalidMCException exception = new InvalidMCException();
        assertEquals(CRUDException.INVALID_DATA_CODE, exception.getExceptionCode());
    }
}
