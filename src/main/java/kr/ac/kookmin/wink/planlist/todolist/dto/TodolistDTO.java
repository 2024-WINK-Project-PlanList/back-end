package kr.ac.kookmin.wink.planlist.todolist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class TodolistDTO {

    private int todoListId;       // 투두 리스트 ID
    private String content;       // 투두 리스트 내용
    private LocalDateTime createdAt; // 생성일
    private int userId;           // 사용자 ID

    // 모든 필드를 사용하는 생성자
    public TodolistDTO(int todoListId, String content, LocalDateTime createdAt, int userId) {
        this.todoListId = todoListId;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
