package kr.ac.kookmin.wink.planlist.individual.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IndividualCalendarResponseDTO {
    private Long calendarId;
    private String calenderName;
    private Long userId;

    //todo: 개인스케쥴 리스트 추가 예정
    //private List<IndividualSchedule> individualScheduleList;
}
