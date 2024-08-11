package kr.ac.kookmin.wink.planlist.individual.calendar.controller;

import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarCreateRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarRequestDTO;
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
     * @param individualCalendarCreateRequestDTO
     * @return 200 or Error
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody IndividualCalendarCreateRequestDTO individualCalendarCreateRequestDTO) {
        individualCalendarService.create(individualCalendarCreateRequestDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 캘린더 호출
     * @param calendarId
     * @return 캘린더 정보 (IndividualCalender)
     */
    @GetMapping("/{calendarId}")
    public ResponseEntity<IndividualCalendarResponseDTO> getIndividualCalender(@PathVariable("calendarId") Long calendarId) {
        return ResponseEntity.ok(individualCalendarService.getIndividualCalendar(calendarId));
    }

    /**
     * 캘린더 이름 수정
     * @param individualCalendarRequestDTO
     * @return 200 or 400
     */
    @PostMapping("/rename")
    public ResponseEntity<?> rename(@RequestBody IndividualCalendarRequestDTO individualCalendarRequestDTO) {
        individualCalendarService.rename(individualCalendarRequestDTO);
        return ResponseEntity.ok().build();
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
