package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_shared_calendar")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserSharedCalendar{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_calendar")
    private SharedCalendar sharedCalendar;

    @ColumnDefault("false")
    @Column(name = "invitation_status", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean invitationStatus;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
