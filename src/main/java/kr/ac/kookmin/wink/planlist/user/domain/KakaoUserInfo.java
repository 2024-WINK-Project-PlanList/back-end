package kr.ac.kookmin.wink.planlist.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class KakaoUserInfo {
    private String nickname;
    private String email;
}
