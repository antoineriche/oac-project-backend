//package com.gaminho.oacproject.dao;
//
//import com.gaminho.oacproject.model.Todo;
//import com.gaminho.oacproject.model.Todo2;
//import org.slf4j.Logger;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//public class TodoDaoImpl implements TodoDao {
//
//    private static List<Todo> TODOS = new ArrayList<>();
//    static {
//        TODOS.add(new Todo(0, 100, "Assurance habitation", false, new Date()));
//        TODOS.add(new Todo(1, 101, "ENGI", false,  new Date()));
//        TODOS.add(new Todo(2, 102, "Post instagram", true, new Date()));
//    }
//
//    private static List<Todo2> TODOS2 = new ArrayList<>();
//    static {
//        TODOS2.add(new Todo2(0, 100, "Assurance habitation", false, new Date()));
//        TODOS2.add(new Todo2(1, 101, "ENGI", false,  new Date()));
//        TODOS2.add(new Todo2(2, 102, "Post instagram", true, new Date()));
//    }
//
//    @Override
//    public List<Todo> findAll() {
//        return TODOS;
//    }
//
//    @Override
//    public List<Todo2> findAll2() {
//        return TODOS2;
//    }
//
//    @Override
//    public Todo findById(int id) {
//        if(TODOS.stream().anyMatch(t -> t.getId() == id)){
//            return TODOS.stream().filter(t -> t.getId() == id).findFirst().get();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public Todo save(Todo todo) {
//        System.out.println("save new todo");
//        TODOS.add(todo);
//        return todo;
//    }
//}
