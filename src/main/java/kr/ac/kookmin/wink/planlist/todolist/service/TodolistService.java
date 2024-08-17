package kr.ac.kookmin.wink.planlist.todolist.service;

import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import kr.ac.kookmin.wink.planlist.todolist.repository.TodolistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodolistService {

    private final TodolistRepository todolistRepository;

    @Autowired
    public TodolistService(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

    // 투두 리스트 생성
    public Todolist createTask(Todolist todolist) {
        return todolistRepository.save(todolist);
    }

    // 모든 투두 리스트 가져오기
    public List<Todolist> getAllTasks() {
        return todolistRepository.findAll();
    }

    // 특정 사용자에 대한 모든 투두 리스트 가져오기
    public List<Todolist> getTasksByUserId(int userId) {
        return todolistRepository.findByUserId(userId);
    }

    // ID로 투두 리스트 가져오기
    public Optional<Todolist> getTaskById(int id) {
        return todolistRepository.findById(id);
    }

    // 투두 리스트 업데이트
    public Optional<Todolist> updateTask(int id, Todolist updatedTodolist) {
        return todolistRepository.findById(id).map(existingTask -> {
            existingTask.setContent(updatedTodolist.getContent());
            existingTask.setCreatedAt(updatedTodolist.getCreatedAt());
            existingTask.setPin(updatedTodolist.isPin());
            existingTask.setUserId(updatedTodolist.getUserId());
            return todolistRepository.save(existingTask);
        });
    }

    // 투두 리스트 삭제
    public boolean deleteTask(int id) {
        return todolistRepository.findById(id).map(existingTask -> {
            todolistRepository.delete(existingTask);
            return true;
        }).orElse(false);
    }
}
