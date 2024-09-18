package kr.ac.kookmin.wink.planlist.todolist.repository;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.stereotype.Repository;
import kr.ac.kookmin.wink.planlist.todolist.domain.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@Repository
public interface TodolistRepository extends JpaRepository<Todolist, Long> {
    // 특정 사용자의 투두리스트를 찾기 위한 메소드
    List<Todolist> findAllByUser(User user);
}
