package kr.ac.kookmin.wink.planlist.shared.calendar.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class InviteSharedCalendarRequestDTO {
    private Long calendarId;
    private List<Long> invitingUsers;
}
