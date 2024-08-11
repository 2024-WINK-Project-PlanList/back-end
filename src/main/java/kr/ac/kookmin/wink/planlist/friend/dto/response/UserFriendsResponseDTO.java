package kr.ac.kookmin.wink.planlist.friend.dto.response;

import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendsResponseDTO {
    private Long friendshipId;
    private Long friendId;
    private String nickname;
    private String songId;
    private String profileImagePath;

    public UserFriendsResponseDTO(Friendship friendship, User standardUser) {

        this(friendship.getId(), friendship.getAnotherOne(standardUser));
    }

    protected UserFriendsResponseDTO(Long friendshipId, User friend) {
        this.friendshipId = friendshipId;
        this.friendId = friend.getId();
        this.nickname = friend.getNickname();
        this.songId = friend.getSongId();
        this.profileImagePath = friend.getProfileImagePath();
    }
}
