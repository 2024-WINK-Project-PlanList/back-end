package kr.ac.kookmin.wink.planlist.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo_list")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("false")
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean checked;

    public void update(String content, boolean checked) {
        this.content = content;
        this.checked = checked;
    }
}
