package com.gaminho.oacproject.dao;

import com.gaminho.oacproject.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoDaoImpl implements TodoDao {

    private static List<Todo> TODOS = new ArrayList<>();
    static {
        TODOS.add(new Todo(0, 100, "Assurance habitation", false));
        TODOS.add(new Todo(1, 101, "ENGI", false));
        TODOS.add(new Todo(2, 102, "Post instagram", true));
    }

    @Override
    public List<Todo> findAll() {
        return TODOS;
    }

    @Override
    public Todo findById(int id) {
        if(TODOS.stream().anyMatch(t -> t.getId() == id)){
            return TODOS.stream().filter(t -> t.getId() == id).findFirst().get();
        } else {
            return null;
        }
    }

    @Override
    public Todo save(Todo todo) {
        TODOS.add(todo);
        return todo;
    }
}
