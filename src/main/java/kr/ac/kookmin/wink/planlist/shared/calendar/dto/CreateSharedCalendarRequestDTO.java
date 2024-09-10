package kr.ac.kookmin.wink.planlist.shared.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CreateSharedCalendarRequestDTO {
    private String name;
    private String description;
    private String imageBase64;
    private List<Long> membersToInvite;
}
