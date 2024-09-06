package kr.ac.kookmin.wink.planlist.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFriendshipRequestDTO {

    private Long followerId;
    private Long followingId;
}
