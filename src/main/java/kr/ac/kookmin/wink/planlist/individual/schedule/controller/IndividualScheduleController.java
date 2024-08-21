package kr.ac.kookmin.wink.planlist.individual.schedule.controller;

import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.service.IndividualScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule/individual")
public class IndividualScheduleController {

    private final IndividualScheduleService individualScheduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        individualScheduleService.createSchedule(individualScheduleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{scheduleId}")
    public IndividualScheduleResponseDTO findSchedule(@PathVariable Long scheduleId) {
        return individualScheduleService.findSchedule(scheduleId);
    }

    @GetMapping("/list/{calendarId}")
    public List<IndividualScheduleResponseDTO> getScheduleList(@PathVariable Long calendarId) {
        return individualScheduleService.getSchedules(calendarId);
    }

    @PatchMapping("/update/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        individualScheduleService.updateSchedule(scheduleId, individualScheduleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        individualScheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }

}
