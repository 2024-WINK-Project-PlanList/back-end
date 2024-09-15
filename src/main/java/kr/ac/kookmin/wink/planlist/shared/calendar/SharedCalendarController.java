package kr.ac.kookmin.wink.planlist.shared.calendar;

import kr.ac.kookmin.wink.planlist.global.jwt.TokenProvider;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.InviteSharedCalendarDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shared-calendar")
public class SharedCalendarController {
    private final SharedCalendarService sharedCalendarService;

    @GetMapping()
    public ResponseEntity<List<SharedCalendarResponseDTO>> getSharedCalendarList(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(sharedCalendarService.getMySharedCalendars(securityUser));
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<SharedCalendarResponseDTO> getSharedCalendar(@PathVariable Long calendarId) {
        return ResponseEntity.ok(sharedCalendarService.getSharedCalendar(calendarId));
    }

    @PostMapping()
    public ResponseEntity<?> createSharedCalendar(@RequestBody CreateSharedCalendarRequestDTO createSharedCalendarRequestDTO, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.createSharedCalendar(createSharedCalendarRequestDTO, securityUser);
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

    @GetMapping("/invite")
    public List<SharedCalendarResponseDTO> invitedSharedCalendarList(@AuthenticationPrincipal SecurityUser securityUser) {
        return sharedCalendarService.invitedSharedCalendarList(securityUser);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteSharedCalendar(@RequestBody InviteSharedCalendarDTO inviteSharedCalendarDTO) {
        sharedCalendarService.invite(inviteSharedCalendarDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invite/{calendarId}")
    public ResponseEntity<?> refuseInvite(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.reject(calendarId, securityUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join/{calendarId}")
    public ResponseEntity<?> joinSharedCalendar(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.join(calendarId, securityUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leave/{calendarId}")
    public ResponseEntity<?> leaveSharedCalendar(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.leave(calendarId, securityUser);
        return ResponseEntity.ok().build();
    }
}
