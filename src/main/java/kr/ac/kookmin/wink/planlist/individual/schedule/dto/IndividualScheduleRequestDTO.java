package kr.ac.kookmin.wink.planlist.individual.schedule.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class IndividualScheduleRequestDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleOpenStatus openStatus;
    private Integer colorId;
    private List<Long> scheduleMembers;
    private Long calendarId;
}
