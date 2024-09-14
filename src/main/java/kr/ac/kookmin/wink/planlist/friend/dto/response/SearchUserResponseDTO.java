package kr.ac.kookmin.wink.planlist.friend.dto.response;


import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserResponseDTO {
    private UserDTO user;
    private boolean isFriend;
}
