package kr.ac.kookmin.wink.planlist.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo_list")
@Getter
@Setter
public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private Long todoListId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // DB에 있는디..?
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateContent(String content) {
        this.content = content;

    }
}
