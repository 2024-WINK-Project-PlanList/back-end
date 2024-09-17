package kr.ac.kookmin.wink.planlist.todolist.controller;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
import kr.ac.kookmin.wink.planlist.todolist.dto.request.CreateTodolistRequestDTO;
import kr.ac.kookmin.wink.planlist.todolist.dto.request.UpdateTodolistRequestDTO;
import kr.ac.kookmin.wink.planlist.todolist.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createTask(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody CreateTodolistRequestDTO requestDTO
    ) {
        todolistService.createTask(securityUser.getUser(), requestDTO);
        return ResponseEntity.ok().build();
    }

    // 할 일 조회
    @GetMapping("/tasks")
    public ResponseEntity<List<TodolistDTO>> getAllTasks(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(todolistService.getTasksByUser(securityUser.getUser()));
    }

    // ID로 할 일 조회
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TodolistDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(todolistService.getTaskById(id));
    }

    // ID로 할 일 업데이트
    @PatchMapping("/tasks")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTodolistRequestDTO requestDTO) {
        todolistService.updateTask(requestDTO);
        return ResponseEntity.ok().build();
    }

    // ID로 할 일 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            todolistService.deleteTask((id));
            return ResponseEntity.noContent().build();
        }
        catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();

        }
    }
}
