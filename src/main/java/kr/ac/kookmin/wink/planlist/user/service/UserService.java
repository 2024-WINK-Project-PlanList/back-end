package kr.ac.kookmin.wink.planlist.user.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.ac.kookmin.wink.planlist.global.config.jwt.TokenProvider;
import kr.ac.kookmin.wink.planlist.user.domain.KakaoUserInfo;
import kr.ac.kookmin.wink.planlist.user.domain.LoginType;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.request.KakaoLoginRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.LoginRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.RegisterRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.KakaoLoginResponseDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.RegisterResponseDTO;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final TokenProvider tokenProvider;

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO, long currentTime) {
        String kakaoAccessToken = registerRequestDTO.getKakaoAccessToken();
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken)
                .orElseThrow(() -> new IllegalArgumentException(kakaoAccessToken));

        User user = User.builder()
                .name(kakaoUserInfo.getName())
                .nickname(registerRequestDTO.getNickname())
                .email(kakaoUserInfo.getEmail())
                .createdAt(new Timestamp(currentTime))
                .build();

        userRepository.save(user);

        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(7));

        return RegisterResponseDTO.builder()
                .accessToken(accessToken)
                .nickname(user.getNickname())
                .build();
    }

    public User login(LoginRequestDTO loginRequestDTO) {
        String accessToken = loginRequestDTO.getAccessToken();

        if (!tokenProvider.validToken(accessToken)) {
            throw new IllegalArgumentException("Invalid access token");
        }

        Long userId = tokenProvider.getUserId(accessToken);

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId in accessToken"));
    }

    public RegisterResponseDTO getOrRegisterTempAccount(long currentTime) {
        String nickname = "테스트";
        User tempUser = userRepository.findByNickname(nickname).orElse(null);

        if (tempUser != null) {
            String accessToken = tokenProvider.generateToken(tempUser, Duration.ofDays(7));

            return RegisterResponseDTO.builder()
                    .accessToken(accessToken)
                    .nickname(nickname)
                    .build();
        }

        return register(new RegisterRequestDTO("temp", nickname), currentTime);
    }

    public boolean checkNicknameValidation(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public KakaoLoginResponseDTO kakaoLogin(KakaoLoginRequestDTO kakaoLoginRequestDTO) {
        String kakaoAccessToken = kakaoLoginRequestDTO.getAccessToken();
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken)
                .orElseThrow(() -> new IllegalArgumentException(kakaoAccessToken));

        LoginType type = LoginType.REGISTER;
        String token = kakaoAccessToken;
        User user = userRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);

        if (user != null) {
            type = LoginType.EXIST;
            token = tokenProvider.generateToken(user, Duration.ofDays(7));
        }

        return KakaoLoginResponseDTO.builder()
                .type(type)
                .token(token)
                .build();
    }

    private Optional<KakaoUserInfo> getKakaoUserInfo(String accessToken) {
        if (accessToken.equals("temp")) {
            return Optional.of(
                    KakaoUserInfo.builder()
                            .name("test")
                            .email("test@gmail.com")
                            .build()
            );
        }

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
                .name(nickname)
                .email(email)
                .build());
    }
}