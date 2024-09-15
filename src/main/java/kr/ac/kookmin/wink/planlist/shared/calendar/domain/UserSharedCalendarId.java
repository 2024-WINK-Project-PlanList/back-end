package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import com.drew.lang.annotations.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserSharedCalendarId implements Serializable {

    @Column(name = "userId")
    @NotNull
    private Long userId;

    @Column(name = "sharedCalendarId")
    @NotNull
    private Long sharedCalendarId;
}
