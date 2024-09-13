package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"sharedCalendar\"")
@Entity
public class SharedCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String imageBase64;

    @OneToMany(mappedBy = "sharedCalendar")
    private List<SharedSchedule> sharedScheduleList;

}
