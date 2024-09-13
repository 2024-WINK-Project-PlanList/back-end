package kr.ac.kookmin.wink.planlist.shared.calendar.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSharedCalendarRepository extends JpaRepository<UserSharedCalendar, Long> {
}
