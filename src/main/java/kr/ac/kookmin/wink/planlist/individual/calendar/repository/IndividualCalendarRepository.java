package kr.ac.kookmin.wink.planlist.individual.calendar.repository;

import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IndividualCalendarRepository extends JpaRepository<IndividualCalendar, Long> {
    Optional<IndividualCalendar> findByUserId(Long userId);
}
