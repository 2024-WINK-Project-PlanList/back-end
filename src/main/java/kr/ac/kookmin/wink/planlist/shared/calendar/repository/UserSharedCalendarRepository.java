package kr.ac.kookmin.wink.planlist.shared.calendar.repository;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendarId;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSharedCalendarRepository extends JpaRepository<UserSharedCalendar, UserSharedCalendarId> {
    List<UserSharedCalendar> findAllByUser(User user);

    void deleteAllBySharedCalendar(SharedCalendar sharedCalendar);

    boolean existsBySharedCalendar(SharedCalendar sharedCalendar);

    List<UserSharedCalendar> findAllBySharedCalendar(SharedCalendar sharedCalendar);

}
