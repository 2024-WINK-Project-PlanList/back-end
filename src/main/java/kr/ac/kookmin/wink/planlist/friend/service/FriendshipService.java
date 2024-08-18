package kr.ac.kookmin.wink.planlist.friend.service;

import kr.ac.kookmin.wink.planlist.friend.domain.FriendStatus;
import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.UserFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.WaitingFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.repository.FriendshipRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구관계 ID입니다."));
    }

    public List<UserFriendsResponseDTO> findAllFriendsByUser(Long userId) {
        User standardUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));

        return standardUser.getFriendshipsByStatus(FriendStatus.FRIEND)
                .stream()
                .map((friendship) -> new UserFriendsResponseDTO(friendship, standardUser))
                .toList();
    }

    public List<WaitingFriendsResponseDTO> findAllWaitingFriendshipsByUser(Long userId, boolean isFollower) {
        User standardUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID입니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("팔로워 ID로 가져온 유저가 존재하지 않습니다."));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 ID로 가져온 유저가 존재하지 않습니다."));

        Friendship friendship = Friendship.builder()
                .follower(follower)
                .following(following)
                .status(FriendStatus.WAITING)
                .requestedAt(new Timestamp(currentTime))
                .build();

        return friendshipRepository.save(friendship);
    }

    public void accept(Long friendshipId) {
        Friendship friendship = findById(friendshipId);

        friendship.setStatus(FriendStatus.FRIEND);
    }

    public void delete(Long friendshipId) {
        friendshipRepository.deleteById(friendshipId);
    }
}
