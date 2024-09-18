package kr.ac.kookmin.wink.planlist.shared.calendar.dto.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CreateSharedCalendarRequestDTO {
    private String name;
    private String description;
    private List<Long> membersToInvite;
}
