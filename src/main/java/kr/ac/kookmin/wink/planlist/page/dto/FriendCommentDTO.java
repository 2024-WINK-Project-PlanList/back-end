package kr.ac.kookmin.wink.planlist.page.dto;

import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendCommentDTO {
    private Long id;
    private String nickname;
    private String profileImagePath;
    private String comment;
    private String song;

    public static FriendCommentDTO create(UserDTO userDTO) {
        return FriendCommentDTO
                .builder()
                .id(userDTO.getId())
                .nickname(userDTO.getNickname())
                .profileImagePath(userDTO.getProfileImagePath())
                .comment(userDTO.getComment())
                .song(userDTO.getSongId())
                .build();
    }
}
