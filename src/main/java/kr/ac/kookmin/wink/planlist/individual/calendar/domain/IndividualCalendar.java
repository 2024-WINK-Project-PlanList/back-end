package kr.ac.kookmin.wink.planlist.individual.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "individual_calendar")
@Entity
public class IndividualCalendar {

    @Id
    @Column(name = "calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "individualCalendar")
    private List<IndividualSchedule> individualScheduleList = new ArrayList<>();
}
