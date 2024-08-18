package kr.ac.kookmin.wink.planlist.individual.schedule.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"individual_schedule\"")
@Entity
public class IndividualSchedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scheduleContent;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(name = "open_status")
    @Enumerated(EnumType.STRING)
    private IndividualScheduleOpenStatus openStatus;

    private Integer colorId;

    @ManyToOne
    @JoinColumn(name = "calender_id")
    private IndividualCalendar individualCalender;
}
