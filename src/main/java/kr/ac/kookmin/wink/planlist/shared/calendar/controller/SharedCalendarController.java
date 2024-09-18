package kr.ac.kookmin.wink.planlist.shared.calendar.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.shared.calendar.service.SharedCalendarService;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.InviteSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.response.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.UpdateSharedCalendarRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shared-calendar")
public class SharedCalendarController {
    private final SharedCalendarService sharedCalendarService;

    @GetMapping
    public ResponseEntity<List<SharedCalendarResponseDTO>> getSharedCalendarList(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(sharedCalendarService.getMySharedCalendars(securityUser.getUser()));
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<SharedCalendarResponseDTO> getSharedCalendar(@PathVariable Long calendarId) {
        return ResponseEntity.ok(sharedCalendarService.getSharedCalendar(calendarId));
    }

    @PostMapping
    public ResponseEntity<?> createSharedCalendar(
            @RequestPart CreateSharedCalendarRequestDTO requestDTO,
            @RequestPart(required = false) MultipartFile image,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        sharedCalendarService.createSharedCalendar(requestDTO, image, securityUser.getUser());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{calendarId}")
    public ResponseEntity<?> updateSharedCalendar(@PathVariable Long calendarId, @RequestBody UpdateSharedCalendarRequestDTO requestDTO) {
        sharedCalendarService.updateSharedCalendar(calendarId, requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<?> deleteSharedCalendar(@PathVariable Long calendarId) {
        sharedCalendarService.deleteSharedCalendar(calendarId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteSharedCalendar(@RequestBody InviteSharedCalendarRequestDTO requestDTO) {
        sharedCalendarService.invite(requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/invite/{calendarId}")
    public ResponseEntity<?> refuseInvite(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.reject(calendarId, securityUser.getUser());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/join/{calendarId}")
    public ResponseEntity<?> joinSharedCalendar(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.join(calendarId, securityUser.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leave/{calendarId}")
    public ResponseEntity<?> leaveSharedCalendar(@PathVariable Long calendarId, @AuthenticationPrincipal SecurityUser securityUser) {
        sharedCalendarService.leave(calendarId, securityUser.getUser());
        return ResponseEntity.ok().build();
    }
}
