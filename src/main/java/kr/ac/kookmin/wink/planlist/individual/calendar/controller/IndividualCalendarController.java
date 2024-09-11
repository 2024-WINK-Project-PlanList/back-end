package kr.ac.kookmin.wink.planlist.individual.calendar.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.service.IndividualCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar")
public class IndividualCalendarController {

    private final IndividualCalendarService individualCalendarService;

    /**
     * calendarId로 캘린더 호출
     * @return 캘린더 정보(IndividualCalendarResponseDTO)
     */
    @GetMapping
    public ResponseEntity<IndividualCalendarResponseDTO> getIndividualCalender(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(individualCalendarService.getIndividualCalendar(securityUser.getUser()));
    }

    /**
     * userId로 캘린더 호출, 친구 캘린더 호출에 사용
     * @param userId
     * @return 캘린더 정보(IndividualCalendarResponseDTO)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<IndividualCalendarResponseDTO> getIndividualCalenderByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(individualCalendarService.getIndividualCalenderByUserId(userId));
    }

    /**
     * 캘린더 삭제
     * @param calendarId
     * @return 200 or 400
     */
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<?> delete(@PathVariable("calendarId") Long calendarId) {
        individualCalendarService.delete(calendarId);
        return ResponseEntity.ok().build();
    }
}
