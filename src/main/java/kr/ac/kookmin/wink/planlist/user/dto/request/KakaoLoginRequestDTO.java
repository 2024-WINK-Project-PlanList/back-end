package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class KakaoLoginRequestDTO {
    private String accessToken;
}
