package kr.ac.kookmin.wink.planlist.shared.calendar.dto.response;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SharedCalendarResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String calendarImagePath;

    public static SharedCalendarResponseDTO create(SharedCalendar sharedCalendar) {
        return SharedCalendarResponseDTO.builder()
                .id(sharedCalendar.getId())
                .name(sharedCalendar.getName())
                .description(sharedCalendar.getDescription())
                .calendarImagePath(sharedCalendar.getCalendarImagePath())
                .build();
    }

}
