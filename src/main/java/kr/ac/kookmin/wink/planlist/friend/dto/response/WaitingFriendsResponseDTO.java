package kr.ac.kookmin.wink.planlist.friend.dto.response;

import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaitingFriendsResponseDTO {
    private Long friendshipId;
    private Long friendId;
    private boolean isFollower;
    private String email;
    private String nickname;
    private String songId;
    private String profileImagePath;

    public WaitingFriendsResponseDTO(Friendship friendship, boolean isFollower) {
        this(friendship.getId(), (isFollower) ? friendship.getFollower() : friendship.getFollowing(), isFollower);
    }

    protected WaitingFriendsResponseDTO(Long friendshipId, User anotherUser, boolean isFollower) {
        this.friendshipId = friendshipId;
        this.friendId = anotherUser.getId();
        this.isFollower = isFollower;
        this.nickname = anotherUser.getNickname();
        this.songId = anotherUser.getSongId();
        this.email = anotherUser.getEmail();
        this.profileImagePath = anotherUser.getProfileImagePath();
    }
}
