package kr.ac.kookmin.wink.planlist.individual.schedule.repository;

import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndividualScheduleRepository extends JpaRepository<IndividualSchedule, Long> {
    Optional<List<IndividualSchedule>> findByIndividualCalendar(IndividualCalendar individualCalendar);
}
