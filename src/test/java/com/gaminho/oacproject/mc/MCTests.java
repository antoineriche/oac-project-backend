package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.model.MC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MCTests {

    @Test
    public void testGetter_getCreationDate() throws NoSuchFieldException, IllegalAccessException {
        final MC mc = new MC();

        final Field field = mc.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(mc, 12);

        final long dResult = mc.getId();

        assertEquals(12, dResult);
    }

    @Test
    public void testMC_setId() {
        MC mc = new MC();
        mc.setId(12);

        assertEquals(mc.getId(), 12);
    }
}
