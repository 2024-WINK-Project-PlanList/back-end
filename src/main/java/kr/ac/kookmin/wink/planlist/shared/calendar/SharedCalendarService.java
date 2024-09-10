package kr.ac.kookmin.wink.planlist.shared.calendar;

import kr.ac.kookmin.wink.planlist.shared.calendar.repository.MemberSharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharedCalendarService {

    private final SharedCalendarRepository sharedCalendarRepository;
    private final MemberSharedCalendarRepository memberSharedCalendarRepository;

}
