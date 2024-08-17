package kr.ac.kookmin.wink.planlist.todolist.repository;

import kr.ac.kookmin.wink.planlist.todolist.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
