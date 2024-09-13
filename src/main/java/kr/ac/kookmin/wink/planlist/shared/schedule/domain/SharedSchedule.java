package kr.ac.kookmin.wink.planlist.shared.schedule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"sharedSchedule\"")
@Entity
public class SharedSchedule {

    @Id
    private Long id;
}
