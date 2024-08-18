package kr.ac.kookmin.wink.planlist.individual.schedule.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@Table(name = "\"individual_schedule\"")
//@Entity
public class IndividualSchedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schedule_content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer openStatus;

//    @ManyToOne
//    @JoinColumn(name = "calender_id")
//    private IndividualCalender individualCalender;
}
