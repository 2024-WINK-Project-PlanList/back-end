package kr.ac.kookmin.wink.planlist.friend.service;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.WaitingFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FriendshipServiceTest {
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        userService.getOrRegisterTempAccount(System.currentTimeMillis());
        registerMockUser("황수민", "test2@gmail.com");
        registerMockUser("장민우", "test3@gmail.com");
    }

    private void registerMockUser(String name, String email) {
        User mockUser = User.builder()
                .name(name)
                .email(email)
                .nickname(name)
                .build();

        userRepository.save(mockUser);
    }

    private User getTempUser() {
        UserDTO userDTO = userService.getOrRegisterTempAccount(-1L).getUser();
        return userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("unexpected user id: " + userDTO.getUserId()));
    }

    private User getMockUser(String name) {
        return userRepository.findByNickname(name)
                .orElseThrow(() -> new IllegalArgumentException("unexpected user nickname: " + name));
    }

    @Test
    @DisplayName("createFriendship: 친구 관계 생성")
    public void testCreateFriendship() {
        //given
        User user1 = getTempUser();
        User user2 = getMockUser("황수민");
        User user3 = getMockUser("장민우");

        //when
        CreateFriendshipRequestDTO requestDTO1 = new CreateFriendshipRequestDTO(user1.getId(), user2.getId());
        Friendship friendship1 = friendshipService.createFriendship(requestDTO1, 100L);

        CreateFriendshipRequestDTO requestDTO2 = new CreateFriendshipRequestDTO(user1.getId(), user3.getId());
        Friendship friendship2 = friendshipService.createFriendship(requestDTO2, 100L);

        //then
        Assertions.assertThat(friendship1.getFollower().getId()).isEqualTo(user1.getId());
        Assertions.assertThat(friendship1.getFollowing().getNickname()).isEqualTo(user2.getNickname());
        Assertions.assertThat(friendship2.getFollower().getId()).isEqualTo(user1.getId());
        Assertions.assertThat(friendship2.getFollowing().getNickname()).isEqualTo(user3.getNickname());
    }

    @Test
    @DisplayName("findAllWaitingFriendshipsByUser: 유저의 친구 목록 조회")
    public void testFindFriendsByUser() {
        //given
        User user1 = getTempUser();
        User user2 = getMockUser("황수민");
        User user3 = getMockUser("장민우");

        //when

        CreateFriendshipRequestDTO requestDTO1 = new CreateFriendshipRequestDTO(user1.getId(), user2.getId());
        Friendship friendship1 = friendshipService.createFriendship(requestDTO1, 100L);

        CreateFriendshipRequestDTO requestDTO2 = new CreateFriendshipRequestDTO(user1.getId(), user3.getId());
        Friendship friendship2 = friendshipService.createFriendship(requestDTO2, 100L);

        List<WaitingFriendsResponseDTO> allWaitingFriendshipsByUser = friendshipService.findAllWaitingFriendshipsByUser(user1.getId(), true);

        //then
        allWaitingFriendshipsByUser
                .forEach((friendship) -> System.out.println(friendship.toString()));

        Assertions.assertThat(allWaitingFriendshipsByUser.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("createFriendship: 친구 관계 생성")
    @Rollback(value = false)
    public void testFollowerFollowingQuery() throws Exception {
        //given
        User user1 = getTempUser();
        User user2 = getMockUser("황수민");
        User user3 = getMockUser("장민우");

        //when

        CreateFriendshipRequestDTO requestDTO1 = new CreateFriendshipRequestDTO(user1.getId(), user2.getId());
        Friendship friendship1 = friendshipService.createFriendship(requestDTO1, 100L);

        CreateFriendshipRequestDTO requestDTO2 = new CreateFriendshipRequestDTO(user3.getId(), user1.getId());
        Friendship friendship2 = friendshipService.createFriendship(requestDTO2, 100L);

        String url1 = "/friend/" + friendship1.getId() + "/accept";
        String url2 = "/friend/" + friendship2.getId() + "/accept";

        //when
        ResultActions result1 = mockMvc.perform(post(url1));
        ResultActions result2 = mockMvc.perform(post(url2));

        //then
        result1
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        result2.andExpect(status().is2xxSuccessful())
                .andDo(print());

//        List<User> users = userRepository.findAllByUserId(user1.getId());
//
//        Assertions.assertThat(users.size()).isEqualTo(2);
//        Assertions.assertThat(users.contains(user2)).isTrue();
//        Assertions.assertThat(users.contains(user2)).isTrue();
    }
}