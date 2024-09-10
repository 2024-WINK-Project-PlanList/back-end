package kr.ac.kookmin.wink.planlist.shared.schedule.repository;

import kr.ac.kookmin.wink.planlist.shared.schedule.domain.MemberSharedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSharedScheduleRepository extends JpaRepository<MemberSharedSchedule, Long> {
}
