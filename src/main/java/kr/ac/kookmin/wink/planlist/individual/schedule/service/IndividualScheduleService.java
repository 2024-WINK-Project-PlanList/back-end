package kr.ac.kookmin.wink.planlist.individual.schedule.service;

import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IndividualScheduleService {

    private final IndividualScheduleRepository individualScheduleRepository;
    private final IndividualCalendarRepository individualCalendarRepository;
    private final UserRepository userRepository;

    public void createSchedule(IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        IndividualCalendar individualCalendar = individualCalendarRepository.findById(individualScheduleRequestDTO.getCalendarId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid calendar id: " + individualScheduleRequestDTO.getCalendarId()));
        List<User> scheduleMemberList = new ArrayList<>();
        for(Long userId:individualScheduleRequestDTO.getScheduleMemberList()) {
            scheduleMemberList.add(userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId)));
        }
        individualScheduleRepository.save(IndividualSchedule.builder()
                        .content(individualScheduleRequestDTO.getContent())
                        .startDate(individualScheduleRequestDTO.getStartDate())
                        .endDate(individualScheduleRequestDTO.getEndDate())
                        .openStatus(individualScheduleRequestDTO.getOpenStatus())
                        .colorId(individualScheduleRequestDTO.getColorId())
                        .scheduleMemberList(scheduleMemberList)
                        .individualCalendar(individualCalendar)
                        .build());
    }

    public IndividualScheduleResponseDTO findSchedule(Long scheduleId) {

        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule id: " + scheduleId));

        return IndividualScheduleResponseDTO.create(individualSchedule);
    }

    public void updateSchedule(Long scheduleId, IndividualScheduleRequestDTO individualScheduleRequestDTO) {
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule id: " + scheduleId));

        List<User> userList = new ArrayList<>();
        for(Long userId:individualScheduleRequestDTO.getScheduleMemberList()) {
            userList.add(userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId)));
        }

//        individualSchedule.updateSchedule(individualScheduleRequestDTO, userList);
//        individualSchedule.setContent(individualScheduleRequestDTO.getContent());
//        individualSchedule.setStartDate(individualScheduleRequestDTO.getStartDate());
//        individualSchedule.setEndDate(individualScheduleRequestDTO.getEndDate());
//        individualSchedule.setOpenStatus(individualScheduleRequestDTO.getOpenStatus());
//        individualSchedule.setColorId(individualScheduleRequestDTO.getColorId());
//        individualSchedule.setScheduleMemberList(userList);
        individualScheduleRepository.save(individualSchedule.updateSchedule(individualScheduleRequestDTO, userList));
    }

    public void deleteSchedule(Long scheduleId) {
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule id: " + scheduleId));

        individualScheduleRepository.delete(individualSchedule);
    }

    public List<IndividualScheduleResponseDTO> getSchedules(Long calendarId) {
        return getSchedules(individualCalendarRepository.findById(calendarId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid calendar id: " + calendarId)));
    }

    public List<IndividualScheduleResponseDTO> getSchedules(IndividualCalendar individualCalendar) {
        List<IndividualSchedule> individualSchedules = individualScheduleRepository.findByIndividualCalendar(individualCalendar)
                .orElseThrow(() -> new IllegalArgumentException("Invalid individual calendar: " + individualCalendar));

        List<IndividualScheduleResponseDTO> responseScheduleList = new ArrayList<>();
        for (IndividualSchedule individualSchedule : individualSchedules) {
            responseScheduleList.add(IndividualScheduleResponseDTO.create(individualSchedule));
        }

        return responseScheduleList;
    }
}
