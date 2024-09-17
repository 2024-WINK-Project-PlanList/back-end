package kr.ac.kookmin.wink.planlist.shared.calendar.dto.request;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class UpdateSharedCalendarRequestDTO {
    private String name;
    private String description;
    private String imageBase64;
}
