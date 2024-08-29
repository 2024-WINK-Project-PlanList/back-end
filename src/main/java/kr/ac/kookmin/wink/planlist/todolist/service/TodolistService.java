package kr.ac.kookmin.wink.planlist.todolist.service;

import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import kr.ac.kookmin.wink.planlist.todolist.repository.TodolistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodolistService {

    private final TodolistRepository todolistRepository;

    @Autowired
    public TodolistService(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    // 투두 리스트 생성
    public TodolistDTO createTask(TodolistDTO todolistDTO) {
        Todolist todolist = convertToEntity(todolistDTO);
        Todolist savedTodolist = todolistRepository.save(todolist);
        return convertToDto(savedTodolist);
    }

    // 모든 투두 리스트 가져오기
    public List<TodolistDTO> getAllTasks() {
        List<Todolist> tasks = todolistRepository.findAll();
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // ID로 투두 리스트 가져오기
    public Optional<TodolistDTO> getTaskById(int id) {
        Optional<Todolist> task = todolistRepository.findById(id);
        return task.map(this::convertToDto);
    }

    // 투두 리스트 업데이트
    public Optional<TodolistDTO> updateTask(int id, TodolistDTO updatedTodolistDTO) {
        return todolistRepository.findById(id).map(existingTask -> {
            existingTask.setContent(updatedTodolistDTO.getContent());
            existingTask.setCreatedAt(updatedTodolistDTO.getCreatedAt());
            existingTask.setUserId(updatedTodolistDTO.getUserId());
            Todolist updatedTask = todolistRepository.save(existingTask);
            return convertToDto(updatedTask);
        });
    }

    // 투두 리스트 삭제
    public boolean deleteTask(int id) {
        return todolistRepository.findById(id).map(existingTask -> {
            todolistRepository.delete(existingTask);
            return true;
        }).orElse(false);
    }



    private Todolist convertToEntity(TodolistDTO todolistDTO) {
        return new Todolist(
                todolistDTO.getContent(),
                todolistDTO.getCreatedAt(),
                todolistDTO.getUserId()
        );
    }

    // 엔티티 -> DTO 변환
    private TodolistDTO convertToDto(Todolist todolist) {
        return new TodolistDTO(
                todolist.getTodoListId(),
                todolist.getContent(),
                todolist.getCreatedAt(),
                todolist.getUserId()
        );
    }
}
