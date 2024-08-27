package kr.ac.kookmin.wink.planlist.todolist.dto;

import java.time.LocalDateTime;

public class TodolistDTO {

    private int todoListId;       // 투두 리스트 ID
    private String content;       // 투두 리스트 내용
    private LocalDateTime createdAt; // 생성일
    private int userId;           // 사용자 ID

    // 기본 생성자
    public TodolistDTO() {}

    // 모든 필드를 사용하는 생성자
    public TodolistDTO(int todoListId, String content, LocalDateTime createdAt, boolean isPin, int userId) {
        this.todoListId = todoListId;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public int getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(int todoListId) {
        this.todoListId = todoListId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // toString 메소드 (디버깅 및 로그용)
    @Override
    public String toString() {
        return "TodolistDTO{" +
                "todoListId=" + todoListId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
