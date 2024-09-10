package kr.ac.kookmin.wink.planlist.shared.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateSharedCalendarRequestDTO {
    private String name;
    private String description;
    private String imageBase64;
}
