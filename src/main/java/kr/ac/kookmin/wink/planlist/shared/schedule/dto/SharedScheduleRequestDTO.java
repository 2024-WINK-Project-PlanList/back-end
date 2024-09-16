package kr.ac.kookmin.wink.planlist.shared.schedule.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SharedScheduleRequestDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleOpenStatus openStatus;
    private Integer colorId;
    private List<Long> scheduleMembers;
    private Long calendarId;
}
