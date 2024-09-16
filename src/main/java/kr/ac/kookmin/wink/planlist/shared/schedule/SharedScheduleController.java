package kr.ac.kookmin.wink.planlist.shared.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shared-schedule")
public class SharedScheduleController {

    private final SharedScheduleService sharedScheduleService;
}
