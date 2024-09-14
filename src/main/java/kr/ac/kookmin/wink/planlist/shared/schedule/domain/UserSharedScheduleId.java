package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

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
public class UserSharedScheduleId implements Serializable {

    @Column(name = "userId")
    private Long userId;

    @Column(name = "sharedScheduleId")
    private Long sharedScheduleId;
}
