package kr.ac.kookmin.wink.planlist.shared.calendar.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.MemberSharedCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSharedCalendarRepository extends JpaRepository<MemberSharedCalendar, Long> {
}
