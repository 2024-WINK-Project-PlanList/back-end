package kr.ac.kookmin.wink.planlist.individual.calendar.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class IndividualCalendarResponseDTO {
    private Long calendarId;
    private UserDTO user;
    private List<IndividualScheduleResponseDTO> individualScheduleList;
}
