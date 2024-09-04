package kr.ac.kookmin.wink.planlist.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {
    private String accessToken;
}
