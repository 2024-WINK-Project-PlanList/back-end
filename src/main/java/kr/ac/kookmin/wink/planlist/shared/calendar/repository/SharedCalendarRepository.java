package kr.ac.kookmin.wink.planlist.shared.calendar.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedCalendarRepository extends JpaRepository<SharedCalendar, Long> {
}
