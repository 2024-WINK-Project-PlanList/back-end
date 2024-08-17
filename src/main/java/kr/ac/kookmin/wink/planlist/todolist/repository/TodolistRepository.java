package kr.ac.kookmin.wink.planlist.todolist.repository;

import kr.ac.kookmin.wink.planlist.todolist.model.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodolistRepository extends JpaRepository<Todolist, Integer> {
    // 특정 사용자의 투두리스트를 찾기 위한 메소드
    List<Todolist> findByUserId(int userId);
}
