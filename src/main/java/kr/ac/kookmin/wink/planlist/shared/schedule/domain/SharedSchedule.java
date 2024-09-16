package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import lombok.*;

import java.time.LocalDate;
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

    @Column(name = "content")
    private String content;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "open_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleOpenStatus openStatus;

    @OneToMany(mappedBy = "sharedSchedule")
    private List<UserSharedSchedule> userSharedScheduleList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private SharedCalendar sharedCalendar;
}
