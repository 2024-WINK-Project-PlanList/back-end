package kr.ac.kookmin.wink.planlist.individual.schedule.service;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.individual.exeption.IndividualErrorCode;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class IndividualScheduleService {

    private final IndividualScheduleRepository individualScheduleRepository;
    private final IndividualCalendarRepository individualCalendarRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSchedule(IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        IndividualCalendar individualCalendar = individualCalendarRepository.findById(individualScheduleRequestDTO.getCalendarId())
                        .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_CALENDAR_ID));
        List<User> scheduleMemberList = new ArrayList<>();
        for(Long userId:individualScheduleRequestDTO.getScheduleMembers()) {
            scheduleMemberList.add(userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_USER_ID)));
        }

        IndividualSchedule schedule = IndividualSchedule.builder()
                .name(individualScheduleRequestDTO.getName())
                .description(individualScheduleRequestDTO.getDescription())
                .startDate(individualScheduleRequestDTO.getStartDate())
                .endDate(individualScheduleRequestDTO.getEndDate())
                .openStatus(individualScheduleRequestDTO.getOpenStatus())
                .colorId(individualScheduleRequestDTO.getColorId())
                .scheduleMemberList(scheduleMemberList)
                .individualCalendar(individualCalendar)
                .build();

        individualScheduleRepository.save(schedule);
    }

    public IndividualScheduleResponseDTO findSchedule(Long scheduleId) {

        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_SCHEDULE_ID));

        return IndividualScheduleResponseDTO.create(individualSchedule);
    }

    @Transactional
    public void updateSchedule(Long scheduleId, IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_SCHEDULE_ID));

        List<User> userList = new ArrayList<>();
        for(Long userId:individualScheduleRequestDTO.getScheduleMembers()) {
            userList.add(userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_USER_ID)));
        }

        individualScheduleRepository.save(individualSchedule.updateSchedule(individualScheduleRequestDTO, userList));
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_SCHEDULE_ID));

        individualScheduleRepository.delete(individualSchedule);
    }

    public List<IndividualScheduleResponseDTO> getSchedules(Long calendarId) {
        return getSchedules(individualCalendarRepository.findById(calendarId)
        .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_CALENDAR_ID)));
    }

    public List<IndividualScheduleResponseDTO> getSchedules(IndividualCalendar individualCalendar) {
        List<IndividualSchedule> individualSchedules = individualScheduleRepository.findByIndividualCalendar(individualCalendar)
                .orElseThrow(() -> new CustomException(IndividualErrorCode.INVALID_CALENDAR));

        List<IndividualScheduleResponseDTO> responseScheduleList = new ArrayList<>();
        for (IndividualSchedule individualSchedule : individualSchedules) {
            responseScheduleList.add(IndividualScheduleResponseDTO.create(individualSchedule));
        }

        return responseScheduleList;
    }
}
