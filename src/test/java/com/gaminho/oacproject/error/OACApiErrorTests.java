package com.gaminho.oacproject.error;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OACApiErrorTests {

    @Test
    public void test_gettersAndSetters() {
        String errMsg = "errorMsg";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errDetail = "detail";
        OACApiError err1 = new OACApiError(errMsg, HttpStatus.BAD_REQUEST, errDetail);

        OACApiError err2 = new OACApiError();
        err2.setErrorDetail(errDetail);
        err2.setErrorMsg(errMsg);
        err2.setStatus(httpStatus);

        assertEquals(err1.getErrorMsg(), err2.getErrorMsg());
        assertEquals(err1.getErrorDetail(), err2.getErrorDetail());
        assertEquals(err1.getStatus(), err2.getStatus());
        assertEquals(err1, err2);
    }
}
