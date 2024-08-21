package kr.ac.kookmin.wink.planlist.individual.schedule.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualScheduleOpenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class IndividualScheduleRequestDTO {
    private String scheduleContent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private IndividualScheduleOpenStatus openStatus;
    private Integer colorId;
    private List<Long> scheduleMemberList;
    private Long calendarId;
}
