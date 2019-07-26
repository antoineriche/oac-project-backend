package com.gaminho.oacproject.web.contoller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gaminho.oacproject.dao.TodoDao;
import com.gaminho.oacproject.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class TodoController {

    @Autowired
    private TodoDao todoDao;

    @GetMapping(value="/todos/{id}")
    public ResponseEntity<Todo> getTodoWithId(@PathVariable(value = "id") long id) {
        Todo todo = todoDao.findById(id);
        if(todo != null) {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/todos")
    public List<Todo> getAllTodos(){
        List<Todo> todo = todoDao.findAll();
        SimpleBeanPropertyFilter todoFilter = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filterList = new SimpleFilterProvider().addFilter("todoFilter", todoFilter);
        MappingJacksonValue filteredTodo = new MappingJacksonValue(todo);
        filteredTodo.setFilters(filterList);
        return todoDao.findAll();
    }

//    @GetMapping(value = "/todos2")
//    public MappingJacksonValue getAllTodos2(){
//        List<Todo2> todo = todoDao.findAll2();
//        SimpleBeanPropertyFilter todoFilter = SimpleBeanPropertyFilter.serializeAllExcept("id","creationDate");
//        FilterProvider filterList = new SimpleFilterProvider().addFilter("todoFilter", todoFilter);
//        MappingJacksonValue filteredTodo = new MappingJacksonValue(todo);
//        filteredTodo.setFilters(filterList);
//        return filteredTodo;
//    }

    @PostMapping(value = "/todos")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo){
        todo.setCreationDate(new Date());
        Todo newTodo = todoDao.save(todo);

        if (newTodo == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newTodo.getId())
                    .toUri();

            return ResponseEntity.created(location).body(newTodo);
        }
    }

}
