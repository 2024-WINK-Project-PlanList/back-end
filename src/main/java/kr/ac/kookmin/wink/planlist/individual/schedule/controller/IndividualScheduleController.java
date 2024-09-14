package kr.ac.kookmin.wink.planlist.individual.schedule.controller;

import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.service.IndividualScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class IndividualScheduleController {

    private final IndividualScheduleService individualScheduleService;

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        individualScheduleService.createSchedule(individualScheduleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<IndividualScheduleResponseDTO> findSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(individualScheduleService.findSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        individualScheduleService.updateSchedule(scheduleId, individualScheduleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        individualScheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
