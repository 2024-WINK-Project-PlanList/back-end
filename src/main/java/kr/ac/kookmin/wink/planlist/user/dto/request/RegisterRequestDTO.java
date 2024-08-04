package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {
    private String kakaoAccessToken;
    private String nickname;
}