package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangeProfileRequestDTO {
    private String nickname;
    private String profileImage;
    private String songId;
    private String comment;
}
