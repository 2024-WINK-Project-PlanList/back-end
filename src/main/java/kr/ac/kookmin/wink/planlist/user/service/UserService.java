package kr.ac.kookmin.wink.planlist.user.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.ac.kookmin.wink.planlist.user.domain.KakaoUserInfo;
import kr.ac.kookmin.wink.planlist.user.domain.LoginType;
import kr.ac.kookmin.wink.planlist.user.dto.request.KakaoLoginRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.KakaoLoginResponseDTO;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public boolean checkNicknameValidation(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public KakaoLoginResponseDTO kakaoLogin(KakaoLoginRequestDTO kakaoLoginRequestDTO) {
        String kakaoAccessToken = kakaoLoginRequestDTO.getAccessToken();
        LoginType type = getLoginType(kakaoAccessToken);
        //TODO: JWT Token 생성으로 교체하기
        String accessToken = (type == LoginType.REGISTER) ? kakaoAccessToken : "test";

        return KakaoLoginResponseDTO.builder()
                .type(type)
                .token(accessToken)
                .build();
    }

    private LoginType getLoginType(String kakaoAccessToken) {
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken)
                .orElseThrow(() -> new IllegalArgumentException(kakaoAccessToken));

        String email = kakaoUserInfo.getEmail();

        User user = userRepository.findByEmail(email).orElse(null);

        return (user == null) ? LoginType.REGISTER : LoginType.EXIST;
    }

    private Optional<KakaoUserInfo> getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                String.class
        );

        String bodyText = response.getBody();

        if (bodyText == null) {
            return Optional.empty();
        }

        JsonObject body = JsonParser.parseString(bodyText).getAsJsonObject();
        JsonObject properties = body.get("properties").getAsJsonObject();
        JsonObject kakaoAccount = body.get("kakao_account").getAsJsonObject();
        String nickname = properties.get("nickname").getAsString();
        String email = kakaoAccount.get("email").getAsString();

        return Optional.of(KakaoUserInfo.builder()
                .nickname(nickname)
                .email(email)
                .build());
    }
}