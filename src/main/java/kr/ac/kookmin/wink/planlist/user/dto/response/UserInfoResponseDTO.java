package kr.ac.kookmin.wink.planlist.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDTO {
    private UserDTO user;
    private int totalFriendCount;
}
