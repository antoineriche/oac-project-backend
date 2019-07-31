package com.gaminho.oacproject;

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
public class SongTests {

    @Test
    public void testGetter_getCreationDate() throws NoSuchFieldException, IllegalAccessException {
        //given
        final Date date = new Date();
        final Song song = new Song();
        final Field field = song.getClass().getDeclaredField("creationDate");
        field.setAccessible(true);
        field.set(song, date);

        final Date dResult = song.getCreationDate();

        //then
        assertEquals(date, song.getCreationDate());
//        assertEquals("field wasn't retrieved properly", dResult, date);
    }

    @Test
    public void testSongToString() {
        //given
        Song song = new Song();
        Date date = new Date();
        song.setId(12);
        song.setTitle("Test");
        song.setCreationDate(date);

        assertEquals("Song{" +
                "id=" + 12 +
                ", title='" + "Test" + '\'' +
                ", creationDate=" + date +
                "}", song.toString());
    }
}
