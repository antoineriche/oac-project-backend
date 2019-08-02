package com.gaminho.oacproject.project;

import com.gaminho.oacproject.model.ProjectType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class ProjectTypeTests {

    @Test
    public void testSongToString() {
        //given
        ProjectType type = new ProjectType();
        type.setId(12);
        type.setLabel("Test");

        assertEquals("ProjectType{" +
                "id=" + 12 +
                ", label='" + "Test" + '\'' +
                '}', type.toString());
    }
}
