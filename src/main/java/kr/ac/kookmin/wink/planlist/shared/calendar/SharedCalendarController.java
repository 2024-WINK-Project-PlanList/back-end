package kr.ac.kookmin.wink.planlist.shared.calendar;

import kr.ac.kookmin.wink.planlist.shared.calendar.dto.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shared-calendar")
public class SharedCalendarController {
    private final SharedCalendarService sharedCalendarService;

    @GetMapping()
    public ResponseEntity<List<SharedCalendarResponseDTO>> getSharedCalendarList() {
        return ResponseEntity.ok(sharedCalendarService.getMySharedCalendars());
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<SharedCalendarResponseDTO> getSharedCalendar(@PathVariable Long calendarId) {
        return ResponseEntity.ok(sharedCalendarService.getSharedCalendar(calendarId));
    }

    @PostMapping()
    public ResponseEntity<?> createSharedCalendar(@RequestBody CreateSharedCalendarRequestDTO createSharedCalendarRequestDTO) {
        sharedCalendarService.createSharedCalendar(createSharedCalendarRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{calendarId}")
    public ResponseEntity<?> updateSharedCalendar(@PathVariable Long calendarId, @RequestBody UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        sharedCalendarService.updateSharedCalendar(calendarId, updateSharedCalendarRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<?> deleteSharedCalendar(@PathVariable Long calendarId) {
        sharedCalendarService.deleteSharedCalendar(calendarId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteSharedCalendar() {
        sharedCalendarService.invite();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invite/{inviteId}")
    public ResponseEntity<?> refuseInvite(@PathVariable Long inviteId) {
        sharedCalendarService.reject(inviteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinSharedCalendar() {
        sharedCalendarService.join();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leave/{calendarId}")
    public ResponseEntity<?> leaveSharedCalendar(@PathVariable Long calendarId) {
        sharedCalendarService.leave(calendarId);
        return ResponseEntity.ok().build();
    }
}
