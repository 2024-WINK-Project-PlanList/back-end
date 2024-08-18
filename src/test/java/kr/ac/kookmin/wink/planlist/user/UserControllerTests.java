package kr.ac.kookmin.wink.planlist.user;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserService userService;

    @DisplayName("nick-validation: 중복 닉네임 검사")
    @Test
    public void checkNicknameValidation() throws Exception {
        //given
        String url = "/auth/nick-validation/test";

        //when
        ResultActions result = mockMvc.perform(get(url));

        //then
        result.andExpect(status().is2xxSuccessful());

        Assertions.assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo("false");
    }

    @DisplayName("temp: 임시 계정 생성/조회")
    @Test
    public void tempAccountTest() throws Exception {
        //given
        String url = "/auth/temp";

        //when
        ResultActions result = mockMvc.perform(post(url));

        //then
        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }
}