package kr.ac.kookmin.wink.planlist.shared.schedule.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedScheduleRepository extends JpaRepository<SharedSchedule, Long> {
    List<SharedSchedule> findAllBySharedCalendar(SharedCalendar sharedCalendar);
}
