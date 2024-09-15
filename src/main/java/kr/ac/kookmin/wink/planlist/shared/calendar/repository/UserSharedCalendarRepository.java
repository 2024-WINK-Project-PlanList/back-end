package kr.ac.kookmin.wink.planlist.shared.calendar.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSharedCalendarRepository extends JpaRepository<UserSharedCalendar, Long> {
    List<UserSharedCalendar> findAllByUser(User user);
}
