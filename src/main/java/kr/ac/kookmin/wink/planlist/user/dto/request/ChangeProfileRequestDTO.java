package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeProfileRequestDTO {
    private String nickname;
    private String profileImage;
    private String songId;
    private String comment;
}
