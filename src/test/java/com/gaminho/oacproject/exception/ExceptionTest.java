package com.gaminho.oacproject.exception;

import com.gaminho.oacproject.exception.mc.InvalidMCException;
import com.gaminho.oacproject.exception.mc.MCException;
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
        assertEquals(MCException.INVALID_MC_CODE, exception.getExceptionCode());
    }
}
