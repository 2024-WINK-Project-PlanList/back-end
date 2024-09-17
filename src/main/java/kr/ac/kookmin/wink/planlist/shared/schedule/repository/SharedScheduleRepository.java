package kr.ac.kookmin.wink.planlist.shared.schedule.repository;

import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedScheduleRepository extends JpaRepository<SharedSchedule, Long> {
}
