package kr.ac.kookmin.wink.planlist.individual.schedule.repository;

import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualScheduleRepository extends JpaRepository<IndividualSchedule, Long> {
}
