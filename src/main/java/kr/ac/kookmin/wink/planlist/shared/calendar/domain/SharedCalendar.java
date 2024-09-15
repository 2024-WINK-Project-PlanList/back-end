package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"shared_calendar\"")
@Entity
public class SharedCalendar {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_base_64")
    private String imageBase64;

    @OneToMany(mappedBy = "sharedCalendar")
    private List<SharedSchedule> sharedScheduleList;

    public void update(UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        this.name = updateSharedCalendarRequestDTO.getName();
        this.description = updateSharedCalendarRequestDTO.getDescription();
        this.imageBase64 = updateSharedCalendarRequestDTO.getImageBase64();
    }


}
