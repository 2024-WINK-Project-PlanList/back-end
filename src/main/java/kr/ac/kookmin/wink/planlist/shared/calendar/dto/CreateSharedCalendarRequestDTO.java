package kr.ac.kookmin.wink.planlist.shared.calendar.dto;

import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
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
    private List<UserDTO> membersToInvite;
}
