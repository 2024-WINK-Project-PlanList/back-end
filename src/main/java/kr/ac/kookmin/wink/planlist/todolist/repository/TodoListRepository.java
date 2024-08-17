package kr.ac.kookmin.wink.planlist.todolist.repository;

import kr.ac.kookmin.wink.planlist.todolist.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    // 추가적인 커스텀 메서드가 필요하다면 여기에 정의할 수 있습니다.
}
