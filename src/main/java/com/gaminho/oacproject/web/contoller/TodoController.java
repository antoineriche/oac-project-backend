package com.gaminho.oacproject.web.contoller;

import com.gaminho.oacproject.dao.TodoDao;
import com.gaminho.oacproject.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class TodoController {

    @Autowired
    private TodoDao todoDao;

    @GetMapping(value="/todos/{id}")
    public Todo getTodoWithId(@PathVariable(value = "id") int id) {
        return todoDao.findById(id);
    }

    @GetMapping(value = "/todos")
    public List<Todo> getAllTodos(){
        return todoDao.findAll();
    }

    @PostMapping(value = "/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo){
        return todoDao.save(todo);
    }

}
