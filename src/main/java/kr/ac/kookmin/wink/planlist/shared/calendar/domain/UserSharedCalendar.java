package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"userSharedCalendar\"")
@Entity
public class UserSharedCalendar implements Persistable<UserSharedCalendarId> {

    @EmbeddedId
    private UserSharedCalendarId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("sharedCalendarId")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedCalendar sharedCalendar;

    private int invitationStatus;

    @CreatedDate
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
