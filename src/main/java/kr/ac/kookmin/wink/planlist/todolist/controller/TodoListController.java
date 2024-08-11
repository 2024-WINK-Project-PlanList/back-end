package kr.ac.kookmin.wink.planlist.todolist.controller;

import kr.ac.kookmin.wink.planlist.todolist.model.TodoList;
import kr.ac.kookmin.wink.planlist.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    // 할 일 생성
    @PostMapping("/tasks")
    public ResponseEntity<TodoList> createTask(@RequestBody TodoList todoList) {
        TodoList createdTask = todoListService.createTask(todoList);
        return ResponseEntity.ok(createdTask);
    }

    // 할 일 조회
    @GetMapping("/tasks")
    public ResponseEntity<List<TodoList>> getAllTasks() {
        List<TodoList> tasks = todoListService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // ID로 할 일 조회
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TodoList> getTaskById(@PathVariable Long id) {
        Optional<TodoList> task = todoListService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ID로 할 일 업데이트
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TodoList> updateTask(@PathVariable Long id, @RequestBody TodoList updatedTodoList) {
        Optional<TodoList> updatedTask = todoListService.updateTask(id, updatedTodoList);
        return updatedTask.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ID로 할 일 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean isDeleted = todoListService.deleteTask(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
