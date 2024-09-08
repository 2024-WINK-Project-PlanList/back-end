package kr.ac.kookmin.wink.planlist.todolist.controller;

import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
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
    public ResponseEntity<TodolistDTO> getTaskById(@PathVariable Long id) {
        TodolistDTO task = todolistService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // ID로 할 일 업데이트
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TodolistDTO> updateTask(@PathVariable Long id, @RequestBody TodolistDTO updatedTodolistDTO) {
        TodolistDTO update = todolistService.updateTask(id, updatedTodolistDTO);
        return ResponseEntity.ok(update);
    }

    // ID로 할 일 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = todolistService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
