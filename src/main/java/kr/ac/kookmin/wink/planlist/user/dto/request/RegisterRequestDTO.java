package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequestDTO {
    private String kakaoAccessToken;
    private String nickname;
}