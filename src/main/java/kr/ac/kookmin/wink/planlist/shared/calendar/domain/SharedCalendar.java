package kr.ac.kookmin.wink.planlist.shared.calendar.domain;

import jakarta.persistence.*;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.UpdateSharedCalendarRequestDTO;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "shared_calendar")
@Entity
public class SharedCalendar {

    @Id
    @Column(name = "shared_calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "calendar_image_path")
    private String calendarImagePath;

    public void update(UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        this.name = updateSharedCalendarRequestDTO.getName();
        this.description = updateSharedCalendarRequestDTO.getDescription();
    }
}
