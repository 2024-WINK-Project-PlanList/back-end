package kr.ac.kookmin.wink.planlist.individual.schedule.service;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.individual.calendar.service.IndividualCalendarService;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.ScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
// todo: 전체 실행만 되게 하기
class IndividualScheduleServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IndividualScheduleRepository individualScheduleRepository;
    @Autowired
    private IndividualCalendarService individualCalendarService;
    @Autowired
    private IndividualCalendarRepository individualCalendarRepository;
    @Autowired
    private IndividualScheduleService individualScheduleService;

    @BeforeEach
    void setUp() {
        userService.getOrRegisterTempAccount(10);
        User user = registerMockUser("황수민", "test2@gmail.com");
        User user1 = registerMockUser("장민우", "test3@gmail.com");

        individualCalendarService.create(user);

        addTestSchedule(111111, individualCalendarRepository.findById(1L).get());
        addTestSchedule(222222, individualCalendarRepository.findById(1L).get());
    }
    private User registerMockUser(String name, String email) {
        User mockUser = User.builder()
                .name(name)
                .email(email)
                .nickname(name)
                .build();

        return userRepository.save(mockUser);
    }
    private IndividualSchedule addTestSchedule(int k, IndividualCalendar individualCalendar) {
        IndividualSchedule individualSchedule = IndividualSchedule.builder()
                .content("test schedule " + String.valueOf(k))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(k/100000))
                .openStatus(ScheduleOpenStatus.PUBLIC)
                .colorId(k)
                .individualCalendar(individualCalendar)
                .build();
        individualScheduleRepository.save(individualSchedule);
        return individualSchedule;
    }

    @Test
    @DisplayName("개인 일정 만들기")
    void createSchedule() {
        //given
        IndividualCalendar individualCalendar = individualCalendarRepository.findById(1L).get();

        List<Long> memberList = new ArrayList<>();
        memberList.add(1L);
        memberList.add(2L);

        IndividualScheduleRequestDTO individualScheduleRequest = new IndividualScheduleRequestDTO(
                "test schedule 100",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                ScheduleOpenStatus.PUBLIC,
                345678,
                memberList,
                1L
        );

        //when
        individualScheduleService.createSchedule(individualScheduleRequest);
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(1L).get();

        //then
        List<IndividualSchedule> individualSchedules = individualScheduleRepository.findByIndividualCalendar(individualCalendar).get();

        //todo: 리스트에서 individualSchedules 안에 있는 객체와 individualSchedule이 같은 내용임에도 객체가 다르게 불러와져서 contains가 안먹힘
        for (IndividualSchedule schedule : individualSchedules) {
            if (schedule.getId().equals(individualSchedule.getId())) {
                return;
            }
        }
        assertThat(false).isTrue();

    }

    @Test
    @DisplayName("개인 일정 및 개인 일정 리스트 호출")
    void findSchedule() {
        //given
        Long scheduleId = 3L;
        IndividualSchedule individualSchedule = addTestSchedule(123456, individualCalendarRepository.findById(1L).get());
        individualScheduleRepository.save(individualSchedule);

        //when
        IndividualScheduleResponseDTO individualScheduleResponseDTO = individualScheduleService.findSchedule(scheduleId);
        List<IndividualScheduleResponseDTO> list = individualScheduleService.getSchedules(1L);

        //then
        for (IndividualScheduleResponseDTO dto : list) {
            if (dto.getId().equals(individualScheduleResponseDTO.getId())) {
                return;
            }
        }
        assertThat(false).isTrue();

    }

    @Test
    @DisplayName("개인 일정 수정")
    void updateSchedule() {
        //given
        Long scheduleId = 1L;

        List<Long> memberList = new ArrayList<>();
        memberList.add(1L);
        memberList.add(2L);

        IndividualScheduleRequestDTO request = new IndividualScheduleRequestDTO(
                "changed content",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                ScheduleOpenStatus.PRIVATE,
                345678,
                memberList,
                1L

        );

        //when
        individualScheduleService.updateSchedule(scheduleId, request);

        //then
        IndividualSchedule individualSchedule = individualScheduleRepository.findById(1L).get();
        assertThat(request.getContent()).isEqualTo(individualSchedule.getContent());
        assertThat(request.getStartDate()).isEqualTo(individualSchedule.getStartDate());
        assertThat(request.getEndDate()).isEqualTo(individualSchedule.getEndDate());
        assertThat(request.getOpenStatus()).isEqualTo(individualSchedule.getOpenStatus());
        assertThat(request.getColorId()).isEqualTo(individualSchedule.getColorId());
        for (User user : individualSchedule.getScheduleMemberList()) {
            assertThat(request.getScheduleMemberList().contains(user.getId())).isTrue();
        }
        assertThat(request.getCalendarId()).isEqualTo(individualSchedule.getIndividualCalendar().getId());
    }

    @Test
    @DisplayName("개인 일정 삭제")
    void deleteSchedule() {
        //given
        Long scheduleId = 1L;

        //when
        individualScheduleService.deleteSchedule(scheduleId);

        //then
        List<IndividualSchedule> individualSchedules = individualScheduleRepository.findAll();
        for (IndividualSchedule individualSchedule : individualSchedules) {
            assertThat(individualSchedule.getId()).isNotEqualTo(scheduleId);
        }
    }

}