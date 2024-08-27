package kr.ac.kookmin.wink.planlist.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo_list")
public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private int todoListId;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_pin", nullable = false)
    private boolean isPin;

    @Column(name = "user_id", nullable = false)
    private int userId;



    public Todolist(String content, LocalDateTime createdAt, int userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Todolist() {}


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

    public int getTodoListId() {
        return todoListId;
    }

    public boolean isPin() {
        return isPin;
    }
}
