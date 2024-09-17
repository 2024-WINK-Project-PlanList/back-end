package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.schedule.dto.SharedScheduleRequestDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"shared_schedule\"")
@Entity
public class SharedSchedule {

    @Id
    @Column(name = "shared_schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "open_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleOpenStatus openStatus;

    @Column(name = "color_id")
    private Integer colorId;

    @ManyToOne
    @JoinColumn(name = "shared_calendar_id")
    private SharedCalendar sharedCalendar;

    public static SharedSchedule create(SharedScheduleRequestDTO requestDTO, SharedCalendar sharedCalendar) {
        return SharedSchedule.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .startDate(LocalDateTime.from(requestDTO.getStartDate()))
                .endDate(LocalDateTime.from(requestDTO.getEndDate()))
                .openStatus(requestDTO.getOpenStatus())
                .sharedCalendar(sharedCalendar)
                .build();
    }

    public void update(SharedScheduleRequestDTO requestDTO, SharedCalendar sharedCalendar) {
        this.name = requestDTO.getName();
        this.description = requestDTO.getDescription();
        this.startDate = LocalDateTime.from(requestDTO.getStartDate());
        this.endDate = LocalDateTime.from(requestDTO.getEndDate());
        this.openStatus = requestDTO.getOpenStatus();
        this.colorId = requestDTO.getColorId();
        this.sharedCalendar = sharedCalendar;
    }
}
