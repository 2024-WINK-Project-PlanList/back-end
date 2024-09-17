package kr.ac.kookmin.wink.planlist.individual.schedule.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "individual_schedule")
@Entity
public class IndividualSchedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: name으로
    @Column(name = "name")
    private String name;

    // todo: 상세(또는 장소)
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

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_schedule",
                joinColumns = @JoinColumn(name = "schedule_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> scheduleMemberList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private IndividualCalendar individualCalendar;

    public IndividualSchedule updateSchedule(IndividualScheduleRequestDTO individualScheduleRequestDTO, List<User> scheduleMemberList) {
        this.name = individualScheduleRequestDTO.getName();
        this.description = individualScheduleRequestDTO.getDescription();
        this.startDate = individualScheduleRequestDTO.getStartDate();
        this.endDate = individualScheduleRequestDTO.getEndDate();
        this.openStatus = individualScheduleRequestDTO.getOpenStatus();
        this.colorId = individualScheduleRequestDTO.getColorId();
        this.scheduleMemberList = scheduleMemberList;
        return this;
    }
}
