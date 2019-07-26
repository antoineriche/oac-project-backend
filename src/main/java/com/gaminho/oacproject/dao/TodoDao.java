package com.gaminho.oacproject.dao;

import com.gaminho.oacproject.model.Todo;
import com.gaminho.oacproject.model.Todo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoDao extends JpaRepository<Todo, Integer> {

    List<Todo> findAll();
//    List<Todo2> findAll2();
    Todo findById(long id);
    Todo save(Todo todo);
}
