package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {
    private String accessToken;
}
