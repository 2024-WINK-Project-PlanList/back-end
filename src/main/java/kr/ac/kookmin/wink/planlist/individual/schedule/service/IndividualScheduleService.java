package kr.ac.kookmin.wink.planlist.individual.schedule.service;

import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IndividualScheduleService {

    private final IndividualScheduleRepository individualScheduleRepository;
}
