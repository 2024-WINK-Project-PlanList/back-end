package kr.ac.kookmin.wink.planlist.todolist.service;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.todolist.dto.TodolistDTO;
import kr.ac.kookmin.wink.planlist.todolist.dto.request.CreateTodolistRequestDTO;
import kr.ac.kookmin.wink.planlist.todolist.dto.request.UpdateTodolistRequestDTO;
import kr.ac.kookmin.wink.planlist.todolist.exception.TodolistErrorCode;
import kr.ac.kookmin.wink.planlist.todolist.domain.Todolist;
import kr.ac.kookmin.wink.planlist.todolist.repository.TodolistRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodolistService {

    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;

    // 투두 리스트 생성
    @Transactional
    public void createTask(User user, CreateTodolistRequestDTO requestDTO) {
        Todolist todolist = convertToEntity(user, requestDTO);
        todolistRepository.save(todolist);
    }

    // 모든 투두 리스트 가져오기
    public List<TodolistDTO> getTasksByUser(User user) {
        List<Todolist> tasks = todolistRepository.findAllByUser(user);
        return tasks.stream()
                .map(TodolistDTO::create)
                .toList();
    }


    // ID로 투두 리스트 가져오기
    public TodolistDTO getTaskById(Long id) {
        Optional<Todolist> task = todolistRepository.findById(id);
        return task
                .map(TodolistDTO::create)
                .orElseThrow(() -> new CustomException(TodolistErrorCode.INVALID_TODOLIST));
    }


    @Transactional
    public void updateTask(UpdateTodolistRequestDTO requestDTO) {
        // ID로 투두리스트를 찾고 없으면 예외를 던짐
        Todolist existingTodolist = todolistRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException(TodolistErrorCode.INVALID_TODOLIST));

        existingTodolist.update(requestDTO.getContent(), requestDTO.isChecked());
    }


//
//    @Transactional
//    public boolean deleteTask(Long id) {
//        if (todolistRepository.existsById(id)) {  // 먼저 해당 ID의 작업이 있는지 확인
//            todolistRepository.deleteById(id);   // 작업이 존재하면 삭제 수행
//            return true;                        // 삭제 성공 시 true 반환
//        }
//        return false;                           // 해당 ID의 작업이 없으면 false 반환
//    }

    @Transactional
    public void deleteTask(Long id) {
        Todolist existingTodolist = todolistRepository.findById(id)
                .orElseThrow( () -> new CustomException(TodolistErrorCode.INVALID_TODOLIST));
        todolistRepository.delete(existingTodolist);
    }


    private Todolist convertToEntity(User user, CreateTodolistRequestDTO requestDTO) {

        return Todolist.builder()
                .content(requestDTO.getContent())
                .user(user)
                .date(requestDTO.getDate())
                .build();
    }
}
