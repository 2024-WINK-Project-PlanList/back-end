package kr.ac.kookmin.wink.planlist.individual.schedule.controller;

import kr.ac.kookmin.wink.planlist.individual.schedule.service.IndividualScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule/individual")
public class IndividualScheduleController {

    private final IndividualScheduleService individualScheduleService;
}
