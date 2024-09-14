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
@Table(name = "\"sharedSchedule\"")
@Entity
public class SharedSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private ScheduleOpenStatus openStatus;

    @OneToMany(mappedBy = "sharedSchedule")
    private List<UserSharedSchedule> userSharedScheduleList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "calendarId")
    private SharedCalendar sharedCalendar;
}
