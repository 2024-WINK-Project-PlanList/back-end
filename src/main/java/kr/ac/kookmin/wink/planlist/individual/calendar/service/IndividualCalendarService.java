package kr.ac.kookmin.wink.planlist.individual.calendar.service;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.individual.exeption.IndividualError;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import kr.ac.kookmin.wink.planlist.individual.schedule.service.IndividualScheduleService;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndividualCalendarService {

    private final IndividualCalendarRepository calenderRepository;
    private final UserRepository userRepository;
    private final IndividualCalendarRepository individualCalendarRepository;
    private final IndividualScheduleService individualScheduleService;

    /**
     * 개인캘린더 생성
     * @param userId
     */
    public void create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(IndividualError.INVALID_USER_ID));

        IndividualCalendar individualCalendar = IndividualCalendar.builder()
                .user(user)
                .build();

        calenderRepository.save(individualCalendar);

    }

    /**
     * 사용자의 개인 캘린더 호출
     * @param calendarId
     * @return 캘린더 정보 (IndividualCalendarResponseDTO)
     */
    public IndividualCalendarResponseDTO getIndividualCalendar(Long calendarId){
        IndividualCalendar individualCalendar = calenderRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(IndividualError.INVALID_CALENDAR_ID));

        List<IndividualScheduleResponseDTO> individualSchedules = individualScheduleService.getSchedules(individualCalendar);

        return IndividualCalendarResponseDTO.builder()
                .calendarId(individualCalendar.getId())
                .userId(individualCalendar.getUser().getId())
                .individualScheduleList(individualSchedules)
                .build();
    }

    /**
     * userId로 개인 캘린더 호출
     * @param userId
     * @return 캘린더 정보 (IndividualCalendarResponseDTO)
     */
    public IndividualCalendarResponseDTO getIndividualCalenderByUserId(Long userId){
        IndividualCalendar individualCalendar = calenderRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(IndividualError.INVALID_USER_ID));

        List<IndividualScheduleResponseDTO> individualSchedules = individualScheduleService.getSchedules(individualCalendar);

        return IndividualCalendarResponseDTO.builder()
                .calendarId(individualCalendar.getId())
                .userId(individualCalendar.getUser().getId())
                .individualScheduleList(individualSchedules)
                .build();
    }

    /**
     * 캘린더 삭제
     * @param calendarId
     */
    public void delete(Long calendarId){
        individualCalendarRepository.deleteById(calendarId);

        if (individualCalendarRepository.findById(calendarId).isEmpty()) {
            System.out.println("successfully deleted individual calendar");
        } else {
            throw new CustomException(IndividualError.INVALID_CALENDAR_ID);
        }
    }

}
