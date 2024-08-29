package kr.ac.kookmin.wink.planlist.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private String accessToken;
    private UserDTO user;
}
