package com.gaminho.oacproject.mc;

import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.model.Song;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MCTests {

    @Test
    public void testGetter_getId() throws NoSuchFieldException, IllegalAccessException {
        final MC mc = new MC();
        final Field field = mc.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(mc, 12);

        assertEquals(12, mc.getId());
    }

    @Test
    public void testMC_setId() {
        MC mc = new MC();
        mc.setId(12);
        assertEquals(12, mc.getId());
    }

    @Test
    public void testMC_setPunchline() {
        MC mc = new MC();
        mc.setPunchline("garga");
        assertEquals("garga", mc.getPunchline());
    }

    @Test
    public void testMC_setAddingDate() {
        MC mc = new MC();
        Date d = new Date();
        mc.setAddingDate(d);
        assertEquals(d, mc.getAddingDate());
    }

    @Test
    public void testSongToString() {
        //given
        MC mc = new MC();
        Date date = new Date();
        mc.setId(12);
        mc.setName("Test");
        mc.setAddingDate(date);

        assertEquals("MC{" +
                "id=" + 12 +
                ", name='" + "Test" + '\'' +
                ", punchline='" + null + '\'' +
                ", description='" + null + '\'' +
                ", imageUrl='" + null + '\'' +
                ", addingDate=" + date +
                '}', mc.toString());
    }
}
