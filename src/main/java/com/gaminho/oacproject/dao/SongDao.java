package com.gaminho.oacproject.dao;

import com.gaminho.oacproject.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDao extends JpaRepository<Song, Long> {

    List<Song> findAll();
    Song findById(long id);
    Song save(Song song);
}
