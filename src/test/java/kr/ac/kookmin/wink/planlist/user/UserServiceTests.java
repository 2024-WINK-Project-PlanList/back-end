package kr.ac.kookmin.wink.planlist.user;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testTempAccount() {
        //given

        //when
        User user = userService.getOrRegisterTempAccount(System.currentTimeMillis());

        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(userRepository.count()).isEqualTo(1);
        Assertions.assertThat(userRepository.existsByNickname("테스트")).isEqualTo(true);
    }
}
