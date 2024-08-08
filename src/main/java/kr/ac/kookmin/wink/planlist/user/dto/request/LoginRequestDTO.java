package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginRequestDTO {
    private String accessToken;
}
