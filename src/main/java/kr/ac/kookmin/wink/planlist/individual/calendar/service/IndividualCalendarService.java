package kr.ac.kookmin.wink.planlist.individual.calendar.service;

import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarCreateRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.dto.IndividualCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IndividualCalendarService {

    private final IndividualCalendarRepository calenderRepository;
    private final UserRepository userRepository;
    private final IndividualCalendarRepository individualCalendarRepository;

    /**
     * 개인 캘린더 생성
     * @param individualCalendarCreateRequestDTO
     */
    public void create(IndividualCalendarCreateRequestDTO individualCalendarCreateRequestDTO) {
        User user = userRepository.findById(individualCalendarCreateRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + individualCalendarCreateRequestDTO.getUserId()));

        IndividualCalendar individualCalendar = IndividualCalendar.builder()
                .calendarName(individualCalendarCreateRequestDTO.getCalendarName())
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid calendar id: " + calendarId));

        return IndividualCalendarResponseDTO.builder()
                .calendarId(individualCalendar.getId())
                .calenderName(individualCalendar.getCalendarName())
                .userId(individualCalendar.getUser().getId())
                //todo: 스케쥴 리스트 추가
                .build();
    }

    /**
     * 캘린더 이름 수정
     * @param individualCalendarRequestDTO
     */
    public void rename(IndividualCalendarRequestDTO individualCalendarRequestDTO){
        IndividualCalendar individualCalendar = individualCalendarRepository.findById(individualCalendarRequestDTO.getCalendarId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid calender id: " + individualCalendarRequestDTO.getCalendarId()));

        individualCalendar.setCalendarName(individualCalendarRequestDTO.getCalendarName());
        calenderRepository.save(individualCalendar);
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
            throw new IllegalArgumentException("failed to delete individual calendar");
        }
    }

}
