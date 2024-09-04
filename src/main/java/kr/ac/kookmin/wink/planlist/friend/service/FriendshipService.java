package kr.ac.kookmin.wink.planlist.friend.service;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.UserFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.WaitingFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.exception.FriendErrorCode;
import kr.ac.kookmin.wink.planlist.friend.repository.FriendshipRepository;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Friendship findById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_FRIENDSHIP_ID));
    }

    public List<UserFriendsResponseDTO> findAllFriendsByUser(Long userId) {
        User standardUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(FriendErrorCode.INVALID_USER_ID));

        return standardUser.getFriendshipsByStatus(FriendStatus.FRIEND)
                .stream()
                .map((friendship) -> new UserFriendsResponseDTO(friendship, standardUser))
                .toList();
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
    public void accept(Long friendshipId) {
        Friendship friendship = findById(friendshipId);

        friendship.setStatus(FriendStatus.FRIEND);
    }

    public void delete(Long friendshipId) {
        friendshipRepository.deleteById(friendshipId);
    }

//    public List<User> test(Long userId) {
//        return userRepository.findAllByUserId(userId);
//    }
}
