package kr.ac.kookmin.wink.planlist.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todo_list")
@Getter
@Setter

public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private int todoListId;

    @Column(name = "content", nullable = false, length = 100)
    private String content;


    // DB에 있는디..?
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @Column(name = "user_id", nullable = false)
    private int userId;

    public Todolist(String content, LocalDateTime createdAt, int userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }




    public Todolist() {}

}
