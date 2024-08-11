package kr.ac.kookmin.wink.planlist.individual.calendar.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class IndividualCalendarResponseDTO {
    private Long calendarId;
    private String calenderName;
    private Long userId;

    //todo: 개인 스케쥴 리스트 추가 예정
    //private List<IndividualSchedule> individualScheduleList;
}
