package kr.ac.kookmin.wink.planlist.shared.calendar.dto;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SharedCalendarResponseDTO {

    private String name;
    private String description;
    private String imageBase64;

    public static SharedCalendarResponseDTO from(SharedCalendar sharedCalendar) {
        return SharedCalendarResponseDTO.builder()
                .name(sharedCalendar.getName())
                .description(sharedCalendar.getDescription())
                .imageBase64(sharedCalendar.getImageBase64())
                .build();
    }

}
