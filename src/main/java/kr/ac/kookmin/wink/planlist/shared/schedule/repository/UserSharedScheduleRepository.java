package kr.ac.kookmin.wink.planlist.shared.schedule.repository;

import kr.ac.kookmin.wink.planlist.shared.schedule.domain.UserSharedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSharedScheduleRepository extends JpaRepository<UserSharedSchedule, Long> {
}
