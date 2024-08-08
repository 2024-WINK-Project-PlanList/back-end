package kr.ac.kookmin.wink.planlist.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private String accessToken;
    private String nickname;
    //TODO: 추후 홈화면 디자인이 완성된 후 필요한 정보 추가하기
}
