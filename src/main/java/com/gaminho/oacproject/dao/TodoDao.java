package com.gaminho.oacproject.dao;

import com.gaminho.oacproject.model.Todo;

import java.util.List;

public interface TodoDao {
    List<Todo> findAll();
    Todo findById(int id);
    Todo save(Todo todo);
}
