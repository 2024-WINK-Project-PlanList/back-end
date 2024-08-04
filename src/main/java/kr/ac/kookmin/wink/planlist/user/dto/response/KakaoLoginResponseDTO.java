package kr.ac.kookmin.wink.planlist.user.dto.response;

import kr.ac.kookmin.wink.planlist.user.domain.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KakaoLoginResponseDTO {
    private LoginType type;
    private String token;
}
