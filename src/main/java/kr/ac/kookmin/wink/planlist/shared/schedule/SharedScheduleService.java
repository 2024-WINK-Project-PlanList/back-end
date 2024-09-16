package kr.ac.kookmin.wink.planlist.shared.schedule;

import kr.ac.kookmin.wink.planlist.shared.schedule.repository.UserSharedScheduleRepository;
import kr.ac.kookmin.wink.planlist.shared.schedule.repository.SharedScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharedScheduleService {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final UserSharedScheduleRepository userSharedScheduleRepository;


}
