package kr.ac.kookmin.wink.planlist.shared.schedule.repository;

import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.UserSharedSchedule;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSharedScheduleRepository extends JpaRepository<UserSharedSchedule, Long> {
    List<UserSharedSchedule> findAllBySharedSchedule(SharedSchedule sharedSchedule);

    void deleteByUser(User user);

    void deleteAllBySharedSchedule(SharedSchedule sharedSchedule);
}
