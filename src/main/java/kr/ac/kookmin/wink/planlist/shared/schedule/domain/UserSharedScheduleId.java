package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

import com.drew.lang.annotations.NotNull;
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
public class UserSharedScheduleId implements Serializable {

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "shared_schedule_id")
    @NotNull
    private Long sharedScheduleId;
}
