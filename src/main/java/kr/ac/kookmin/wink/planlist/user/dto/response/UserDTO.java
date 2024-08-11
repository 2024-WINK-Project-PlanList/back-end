package kr.ac.kookmin.wink.planlist.user.dto.response;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String profileImagePath;
    private String songId;

    public static UserDTO create(User user) {
        return UserDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImagePath(user.getProfileImagePath())
                .songId(user.getSongId())
                .build();
    }
}
