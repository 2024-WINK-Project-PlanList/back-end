package kr.ac.kookmin.wink.planlist.friend.dto.response;

import kr.ac.kookmin.wink.planlist.friend.domain.Friendship;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendsResponseDTO {
    private Long friendshipId;
    private UserDTO friend;

    public UserFriendsResponseDTO(Friendship friendship, User standardUser) {
        this(friendship.getId(), friendship.getAnotherOne(standardUser));
    }

    protected UserFriendsResponseDTO(Long friendshipId, User friendUser) {
        this.friendshipId = friendshipId;
        this.friend = UserDTO.create(friendUser);
    }
}
