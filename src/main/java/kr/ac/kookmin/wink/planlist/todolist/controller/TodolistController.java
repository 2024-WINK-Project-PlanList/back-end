package kr.ac.kookmin.wink.planlist.todolist.controller;

import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
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
    public ResponseEntity<TodolistDTO> createTask(@RequestBody TodolistDTO todolistDTO) {
        TodolistDTO createdTask = todolistService.createTask(todolistDTO);
        return ResponseEntity.ok(createdTask);
    }

    // 할 일 조회
    @GetMapping("/tasks")

    public ResponseEntity<List<TodolistDTO>> getAllTasks() {
        List<TodolistDTO> tasks = todolistService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // ID로 할 일 조회
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TodolistDTO> getTaskById(@PathVariable int id) {
        //return ResponseEntity.ok(todolistService.getTaskById(id));
        return todolistService.getTaskById(id).map(ResponseEntity::ok).orElseThrow(() -> new IllegalArgumentException("test"));
    }

    // ID로 할 일 업데이트
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TodolistDTO> updateTask(@PathVariable int id, @RequestBody TodolistDTO updatedTodolistDTO) {
        Optional<TodolistDTO> updatedTask = todolistService.updateTask(id, updatedTodolistDTO);
        return updatedTask.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ID로 할 일 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        boolean deleted = todolistService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
