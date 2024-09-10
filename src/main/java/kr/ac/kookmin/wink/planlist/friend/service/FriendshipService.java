package kr.ac.kookmin.wink.planlist.friend.service;

import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.dto.request.AcceptFriendRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.SearchUserResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.UserFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.WaitingFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.exception.FriendErrorCode;
import kr.ac.kookmin.wink.planlist.friend.repository.FriendshipRepository;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Friendship findById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_FRIENDSHIP_ID));
    }

    public List<UserFriendsResponseDTO> findAllFriendsByUserId(Long userId) {
        User standardUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_USER_ID));

        return findAllFriendsByUser(standardUser);
    }

    public List<UserFriendsResponseDTO> findAllFriendsByUser(User standardUser) {

        return getUserFriendships(standardUser)
                .stream()
                .map((friendship) -> new UserFriendsResponseDTO(friendship, standardUser))
                .toList();
    }

    private List<Friendship> getUserFriendships(User user) {
        List<Friendship> allByFollower = friendshipRepository.findAllByFollower(user);
        List<Friendship> allByFollowing = friendshipRepository.findAllByFollowing(user);

        ArrayList<Friendship> friendships = new ArrayList<>();

        friendships.addAll(allByFollower);
        friendships.addAll(allByFollowing);

        return friendships;
    }

    public List<WaitingFriendsResponseDTO> findAllWaitingFriendshipsByUser(Long userId, boolean isFollower) {
        User standardUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_USER_ID));

        List<Friendship> friendships = (isFollower) ?
                friendshipRepository.findAllByFollower(standardUser) :
                friendshipRepository.findAllByFollowing(standardUser);

        return friendships
                .stream()
                .map((friendship) -> new WaitingFriendsResponseDTO(friendship, isFollower))
                .toList();
    }

    //TODO: 친구 이름 검색 구현
    public List<SearchUserResponseDTO> findAllUsersBySearch(String keyword, boolean onlyFriends) {
        return null;
    }

    @Transactional
    public Friendship createFriendship(CreateFriendshipRequestDTO requestDTO, Long currentTime) {
        Long followerId = requestDTO.getFollowerId();
        Long followingId = requestDTO.getFollowingId();
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_USER_ID));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_USER_ID));

        Friendship friendship = Friendship.builder()
                .follower(follower)
                .following(following)
                .status(FriendStatus.WAITING)
                .requestedAt(new Timestamp(currentTime))
                .build();

        return friendshipRepository.save(friendship);
    }

    @Transactional
    public void accept(AcceptFriendRequestDTO requestDTO) {
        Friendship friendship = findById(requestDTO.getFriendshipId());

        friendship.setStatus(FriendStatus.FRIEND);
    }

    @Transactional
    public void delete(Long friendshipId) {
        friendshipRepository.deleteById(friendshipId);
    }

//    public List<User> test(Long userId) {
//        return userRepository.findAllByUserId(userId);
//    }
}
