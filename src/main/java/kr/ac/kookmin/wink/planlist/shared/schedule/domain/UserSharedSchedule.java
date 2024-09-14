package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
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
@Table(name = "\"userSharedSchedule\"")
@Entity
public class UserSharedSchedule implements Persistable<UserSharedScheduleId> {

    @EmbeddedId
    private UserSharedScheduleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("sharedScheduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedSchedule sharedSchedule;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private ScheduleOpenStatus openStatus;

    @CreatedDate
    private LocalDate createdAt;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
