package kr.ac.kookmin.wink.planlist.todolist.controller;

import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import kr.ac.kookmin.wink.planlist.todolist.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todolist")
public class TodolistController {

    private final TodolistService todolistService;

    @Autowired
    public TodolistController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    // 할 일 생성
    @PostMapping("/tasks")
    public ResponseEntity<Todolist> createTask(@RequestBody Todolist todolist) {
        Todolist createdTask = todolistService.createTask(todolist);
        return ResponseEntity.ok(createdTask);
    }

    // 할 일 조회
    @GetMapping("/tasks")
    public ResponseEntity<List<Todolist>> getAllTasks() {
        List<Todolist> tasks = todolistService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // ID로 할 일 조회
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Todolist> getTaskById(@PathVariable int id) {
        Optional<Todolist> task = todolistService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ID로 할 일 업데이트
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Todolist> updateTask(@PathVariable int id, @RequestBody Todolist updatedTodolist) {
        Optional<Todolist> updatedTask = todolistService.updateTask(id, updatedTodolist);
        return updatedTask.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ID로 할 일 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        boolean deleted = todolistService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }}


