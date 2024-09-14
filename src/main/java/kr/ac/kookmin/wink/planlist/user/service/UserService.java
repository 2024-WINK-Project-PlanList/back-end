package kr.ac.kookmin.wink.planlist.user.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.jwt.TokenProvider;
import kr.ac.kookmin.wink.planlist.global.s3.S3Service;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.individual.calendar.service.IndividualCalendarService;
import kr.ac.kookmin.wink.planlist.user.domain.KakaoUserInfo;
import kr.ac.kookmin.wink.planlist.user.domain.LoginType;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.request.*;
import kr.ac.kookmin.wink.planlist.user.dto.response.KakaoLoginResponseDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.RegisterResponseDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserInfoResponseDTO;
import kr.ac.kookmin.wink.planlist.user.exception.UserErrorCode;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final TokenProvider tokenProvider;
    private final S3Service s3Service;
    private final FriendshipService friendshipService;
    private final IndividualCalendarService calendarService;

    @Transactional
    public void updateSong(SongRequestDTO requestDTO, User user) {
        user.setSongId(requestDTO.getSongId());
    }

    @Transactional
    public void changeUserProfile(ChangeProfileRequestDTO requestDTO, User user) {
        user.updateUserProfile(requestDTO.getNickname(), requestDTO.getSongId(), requestDTO.getComment());
        uploadUserProfileImage(user, requestDTO.getProfileImage());
    }

    private void uploadUserProfileImage(User user, String imageBase64) {
        String randomName = UUID.randomUUID().toString();
        String imagePath = s3Service.uploadBase64Image(imageBase64, "profile/user/", randomName);

        user.setProfileImagePath(imagePath);
    }

    public UserInfoResponseDTO getCurrentUserInfo(SecurityUser securityUser) {
        User user = securityUser.getUser();
        UserDTO userDTO = UserDTO.create(user);
        int count = friendshipService.findAllFriendsByUser(user).size();

        return UserInfoResponseDTO
                .builder()
                .user(userDTO)
                .totalFriendCount(count)
                .build();
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO, long currentTime) {
        String kakaoAccessToken = registerRequestDTO.getKakaoAccessToken();
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken)
                .orElseThrow(() -> new CustomException(UserErrorCode.INVALID_KAKAO_ACCESS_TOKEN));

        User user = User.builder()
                .name(kakaoUserInfo.getName())
                .nickname(registerRequestDTO.getNickname())
                .email(kakaoUserInfo.getEmail())
                .createdAt(new Timestamp(currentTime))
                .build();

        userRepository.save(user);

        calendarService.create(user);

        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(7));

        return RegisterResponseDTO.builder()
                .accessToken(accessToken)
                .user(UserDTO.create(user))
                .build();
    }

    public UserDTO login(LoginRequestDTO loginRequestDTO) {
        String accessToken = loginRequestDTO.getAccessToken();

        if (!tokenProvider.validToken(accessToken)) {
            throw new CustomException(UserErrorCode.INVALID_ACCESS_TOKEN);
        }

        User user = userRepository
                .findById(tokenProvider.getUserId(accessToken))
                .orElseThrow(() -> new CustomException(UserErrorCode.INVALID_ACCESS_TOKEN));

        return UserDTO.create(user);
    }
    
    @Transactional
    public RegisterResponseDTO getOrRegisterTempAccount(long currentTime) {
        String nickname = "테스트";
        User tempUser = userRepository.findByNickname(nickname).orElse(null);

        if (tempUser != null) {
            String accessToken = tokenProvider.generateToken(tempUser, Duration.ofDays(7));

            return RegisterResponseDTO.builder()
                    .accessToken(accessToken)
                    .user(UserDTO.create(tempUser))
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
                .orElseThrow(() -> new CustomException(UserErrorCode.INVALID_KAKAO_ACCESS_TOKEN));

        LoginType type = LoginType.REGISTER;
        String token = kakaoAccessToken;
        Optional<User> user = userRepository.findByEmail(kakaoUserInfo.getEmail());


        if (user.isPresent()) {
            type = LoginType.EXIST;
            token = tokenProvider.generateToken(user.get(), Duration.ofDays(7));
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

        //System.out.println(bodyText);

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