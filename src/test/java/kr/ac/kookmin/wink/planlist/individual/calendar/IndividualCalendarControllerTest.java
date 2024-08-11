package kr.ac.kookmin.wink.planlist.individual.calendar;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.individual.calendar.domain.IndividualCalendar;
import kr.ac.kookmin.wink.planlist.individual.calendar.repository.IndividualCalendarRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IndividualCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private IndividualCalendarRepository individualCalendarRepository;

    @DisplayName("캘린더 생성 테스트")
    @Test
    void create() throws Exception {
        //given
        String url = "/calendar/individual/create";
        String requestJson = "{" +
                "\"calenderName\":\"test\"," +
                "\"userId\":\"1\"" +
                "}";

        UserDTO userDTO = userService.getOrRegisterTempAccount(1L).getUser();

        User user = User.builder()
                .id(userDTO.getUserId())
                .name("test")
                .build();

        IndividualCalendar responseCalendar = IndividualCalendar.builder()
                .id(1L)
                .calendarName("test")
                .user(user)
                .build();

        //when & then

        ResultActions result = mockMvc
                .perform(post(url)
                .contentType("application/json")
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("캘린더 호출 테스트")
    @Test
    void getIndividualCalender() throws Exception {
        //given
        String url = "/calendar/individual/4";

        UserDTO userDTO = userService.getOrRegisterTempAccount(1L).getUser();

        User user = User.builder()
                .id(userDTO.getUserId())
                .name("test")
                .build();

        IndividualCalendar individualCalendar = IndividualCalendar.builder()
                .calendarName("testcalendar2")
                .user(user)
                .build();

        individualCalendarRepository.save(individualCalendar);

        //when & then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @DisplayName("캘린더 이름 수정 테스트")
    @Test
    void rename() throws Exception {
        //given
        String url = "/calendar/individual/rename";

        UserDTO userDTO = userService.getOrRegisterTempAccount(1L).getUser();

        User user = User.builder()
                .id(userDTO.getUserId())
                .name("test")
                .build();

        IndividualCalendar individualCalendar = IndividualCalendar.builder()
                .id(3L)
                .calendarName("testcalendar")
                .user(user)
                .build();

        individualCalendarRepository.save(individualCalendar);

        String requestJson = "{" +
                "\"calendarId\":3," +
                "\"calendarName\":\"test\"," +
                "\"userId\":\"1\"" +
                "}";

        //when & then
        mockMvc.perform(post(url).contentType("application/json").content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("캘린더 삭제 테스트")
    @Test
    void delete() throws Exception {
        //given
        String url = "/calendar/individual/delete/1";

        UserDTO userDTO = userService.getOrRegisterTempAccount(1L).getUser();

        User user = User.builder()
                .id(userDTO.getUserId())
                .name("test")
                .build();

        IndividualCalendar individualCalendar = IndividualCalendar.builder()
                .id(1L)
                .calendarName("testcalendar")
                .user(user)
                .build();

        individualCalendarRepository.save(individualCalendar);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(status().isOk())
                .andDo(print());

    }
}