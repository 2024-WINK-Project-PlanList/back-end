package kr.ac.kookmin.wink.planlist.shared.schedule;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.shared.schedule.dto.SharedScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.schedule.dto.SharedScheduleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shared-schedule")
public class SharedScheduleController {

    private final SharedScheduleService sharedScheduleService;

    @PostMapping
    public ResponseEntity<?> createSharedSchedule(@RequestBody SharedScheduleRequestDTO requestDTO) {
        sharedScheduleService.createSharedSchedule(requestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<List<SharedScheduleResponseDTO>> getAllSharedSchedules(@PathVariable Long calendarId) {
        return ResponseEntity.ok(sharedScheduleService.getAllSharedSchedule(calendarId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<?> updateSharedSchedule(@PathVariable Long scheduleId, @RequestBody SharedScheduleRequestDTO requestDTO) {
        sharedScheduleService.updateSharedSchedule(scheduleId, requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteSharedSchedule(@PathVariable Long scheduleId) {
        sharedScheduleService.deleteSharedSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
