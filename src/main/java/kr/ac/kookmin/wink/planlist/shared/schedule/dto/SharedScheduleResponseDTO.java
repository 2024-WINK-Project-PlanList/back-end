package kr.ac.kookmin.wink.planlist.shared.schedule.dto;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SharedScheduleResponseDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleOpenStatus openStatus;
    private Integer colorId;
    private List<UserDTO> scheduleMembers;

    public static SharedScheduleResponseDTO convertToDTO(SharedSchedule sharedSchedule, List<UserDTO> scheduleMembers) {
        return SharedScheduleResponseDTO.builder()
                .id(sharedSchedule.getId())
                .name(sharedSchedule.getName())
                .description(sharedSchedule.getDescription())
                .startDate(LocalDateTime.from(sharedSchedule.getStartDate()))
                .endDate(LocalDateTime.from(sharedSchedule.getEndDate()))
                .openStatus(sharedSchedule.getOpenStatus())
                .colorId(sharedSchedule.getColorId())
                .scheduleMembers(scheduleMembers)
                .build();
    }
}
