package kr.ac.kookmin.wink.planlist.individual.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.individual.calendar.service.IndividualCalendarService;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualSchedule;
import kr.ac.kookmin.wink.planlist.individual.schedule.domain.IndividualScheduleOpenStatus;
import kr.ac.kookmin.wink.planlist.individual.schedule.dto.IndividualScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.individual.schedule.repository.IndividualScheduleRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IndividualScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @BeforeEach
    public void beforeEach() {
        userService.getOrRegisterTempAccount(10);
        registerMockUser("황수민", "test2@gmail.com");
        registerMockUser("장민우", "test3@gmail.com");

        individualCalendarService.create(1L);

        addTestSchedule(111111, individualCalendarRepository.findById(1L).get());
        addTestSchedule(222222, individualCalendarRepository.findById(1L).get());

    }
    private void registerMockUser(String name, String email) {
        User mockUser = User.builder()
                .name(name)
                .email(email)
                .nickname(name)
                .build();

        userRepository.save(mockUser);
    }
    private void addTestSchedule(int k, IndividualCalendar individualCalendar) {
        individualScheduleRepository.save(IndividualSchedule.builder()
                .scheduleContent("test schedule " + String.valueOf(k))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(k/100000))
                .openStatus(IndividualScheduleOpenStatus.PUBLIC)
                .colorId(k)
                .individualCalendar(individualCalendar)
                .build());
    }

    @Test
    @DisplayName("개인 일정 만들기")
    void createSchedule() throws Exception {
        //given
        String url = "/schedule/individual/create";
        List<Long> memberList = new ArrayList<>();
        memberList.add(1L);
        memberList.add(2L);


        IndividualScheduleRequestDTO individualScheduleRequest = new IndividualScheduleRequestDTO(
                "test schedule 100",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                IndividualScheduleOpenStatus.PUBLIC,
                345678,
                memberList,
                1L
        );
        String body = objectMapper.writeValueAsString(individualScheduleRequest);

        //when&then
        mockMvc
                .perform(post(url).content(body))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("개인 일정 호출")
    void getSchedule() throws Exception {
        //given

        //when&then

    }

    @Test
    @DisplayName("개인 일정 리스트 호출")
    void getSchedules() throws Exception {
        //given

        //when&then

    }

    @Test
    @DisplayName("개인 일정 수정")
    void updateSchedule() throws Exception {
        //given

        //when&then

    }

    @Test
    @DisplayName("개인 일정 삭제")
    void deleteSchedule() throws Exception {
        //given

        //when

        //then

    }

}
