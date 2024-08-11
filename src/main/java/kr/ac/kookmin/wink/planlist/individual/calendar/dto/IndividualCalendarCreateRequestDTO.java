package kr.ac.kookmin.wink.planlist.individual.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IndividualCalendarCreateRequestDTO {
    private String calendarName;
    private Long userId;
}
