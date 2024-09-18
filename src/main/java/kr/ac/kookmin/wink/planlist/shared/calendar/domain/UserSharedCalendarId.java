package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class UserSharedCalendarId implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "shared_calendar_id")
    private long sharedCalendarId;
}
