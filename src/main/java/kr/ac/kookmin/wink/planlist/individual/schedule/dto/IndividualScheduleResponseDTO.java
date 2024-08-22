package kr.ac.kookmin.wink.planlist.individual.schedule.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class IndividualScheduleResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleOpenStatus openStatus;
    private Integer colorId;
    private List<Long> scheduleMemberList;
    private Long calenderId;

    public static IndividualScheduleResponseDTO create(IndividualSchedule schedule) {
        List<Long> scheduleMemberList = new ArrayList<>();

        if (schedule.getScheduleMemberList() != null) {
            for (User user : schedule.getScheduleMemberList()) {
                scheduleMemberList.add(user.getId());
            }
        }

        return IndividualScheduleResponseDTO.builder()
                .id(schedule.getId())
                .content(schedule.getContent())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .openStatus(schedule.getOpenStatus())
                .colorId(schedule.getColorId())
                .scheduleMemberList(scheduleMemberList)
                .calenderId(schedule.getIndividualCalendar().getId())
                .build();
    }
}
