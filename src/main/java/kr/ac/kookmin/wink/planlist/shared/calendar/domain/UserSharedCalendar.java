package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"user_shared_calendar\"")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserSharedCalendar implements Persistable<UserSharedCalendarId> {

    @EmbeddedId
    private UserSharedCalendarId id = new UserSharedCalendarId();

    @MapsId("user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("shared_calendar_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedCalendar sharedCalendar;

    @ColumnDefault("false")
    @Column(name = "invitation_status", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean invitationStatus;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Override
    public UserSharedCalendarId getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
