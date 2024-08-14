package kr.ac.kookmin.wink.planlist.individual.calendar.controller;

import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.service.IndividualCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar/individual")
public class IndividualCalendarController {

    private final IndividualCalendarService individualCalendarService;

    /**
     * 캘린더 생성
     * @param userId
     * @return 200 or Error
     */
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> create(@PathVariable Long userId) {
        individualCalendarService.create(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * calendarId로 캘린더 호출
     * @param calendarId
     * @return 캘린더 정보 (IndividualCalendarResponseDTO)
     */
    @GetMapping("/my/{calendarId}")
    public ResponseEntity<IndividualCalendarResponseDTO> getIndividualCalender(@PathVariable("calendarId") Long calendarId) {
        return ResponseEntity.ok(individualCalendarService.getIndividualCalendar(calendarId));
    }

    /**
     * userId로 캘린더 호출, 친구 캘린더 호출에 사용
     * @param userId
     * @return 캘린더 정보 (IndividualCalendarResponseDTO)
     */
    @GetMapping("/other/{userId}")
    public ResponseEntity<IndividualCalendarResponseDTO> getIndividualCalenderByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(individualCalendarService.getIndividualCalenderByUserId(userId));
    }

    /**
     * 캘린더 삭제
     * @param calendarId
     * @return 200 or 400
     */
    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity<?> delete(@PathVariable("calendarId") Long calendarId) {
        individualCalendarService.delete(calendarId);
        return ResponseEntity.ok().build();
    }

}
