package kr.ac.kookmin.wink.planlist.todolist.service;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
import kr.ac.kookmin.wink.planlist.todolist.exception.TodolistErrorCode;
import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import kr.ac.kookmin.wink.planlist.todolist.repository.TodolistRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.exception.UserErrorCode;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodolistService {

    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;

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
    public TodolistDTO getTaskById(Long id) {
        Optional<Todolist> task = todolistRepository.findById(id);
        return task.map(this::convertToDto).orElseThrow(() -> new CustomException(TodolistErrorCode.INVALID_TODOLIST));
    }


    @Transactional
    public TodolistDTO updateTask(Long id, TodolistDTO updateTodolistDTO) {
        // ID로 투두리스트를 찾고 없으면 예외를 던짐
        Todolist existingTodolist = todolistRepository.findById(id)
                .orElseThrow(() -> new CustomException(TodolistErrorCode.INVALID_TODOLIST));
        return convertToDto(existingTodolist);
    }



    @Transactional
    public boolean deleteTask(Long id) {
        if (todolistRepository.existsById(id)) {  // 먼저 해당 ID의 작업이 있는지 확인
            todolistRepository.deleteById(id);   // 작업이 존재하면 삭제 수행
            return true;                        // 삭제 성공 시 true 반환
        }
        return false;                           // 해당 ID의 작업이 없으면 false 반환
    }


    private Todolist convertToEntity(TodolistDTO todolistDTO) {
        User user = userRepository.findById((long) todolistDTO.getUserId())
                .orElseThrow(() -> new CustomException(TodolistErrorCode.INVALID_USER));

        return Todolist.builder()
                .content(todolistDTO.getContent())
                .user(user)
                
                .build();
    }


    // 엔티티 -> DTO 변환
    private TodolistDTO convertToDto(Todolist todolist) {
        return new TodolistDTO(
                todolist.getTodoListId(),
                todolist.getContent(),
                todolist.getCreatedAt(),
                todolist.getUser().getId()
        );
    }
}
