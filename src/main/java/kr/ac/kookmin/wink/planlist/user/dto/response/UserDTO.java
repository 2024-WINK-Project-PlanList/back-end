package kr.ac.kookmin.wink.planlist.user.dto.response;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String profileImagePath;
    private String comment;
    private String songId;

    public static UserDTO create(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .comment(user.getComment())
                .profileImagePath(user.getProfileImagePath())
                .songId(user.getSongId())
                .build();
    }
}
