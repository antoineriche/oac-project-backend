package com.gaminho.oacproject.utils;

import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.model.ProjectType;
import com.gaminho.oacproject.model.Song;

import java.util.Date;

public class DefaultValues {

    // DEFAULT MC
    public static MC DEFAULT_MC_1 = new MC(1L, "NeirDa", "Plus facile de demander le pardon que la permission.",
            "Neird'insomniak", "", new Date());

    public static MC DEFAULT_MC_2 = new MC(2L, "Kimay", "Wesh",
            "Kim", "", new Date());

    // DEFAULT SONG
    public static Song DEFAULT_SONG_1 = new Song(1L, "SONG1", new Date());
    public static Song DEFAULT_SONG_2 = new Song(2L, "SONG2", new Date());

    //DEFAULT PROJECT TYPES
    public static ProjectType DEFAULT_PROJECT_TYPE_1 = new ProjectType(1L, "Type 1");
    public static ProjectType DEFAULT_PROJECT_TYPE_2 = new ProjectType(2L, "Type 2");

}
