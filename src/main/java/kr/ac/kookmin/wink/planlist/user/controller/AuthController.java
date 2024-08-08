package kr.ac.kookmin.wink.planlist.user.controller;

import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.request.KakaoLoginRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.LoginRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.RegisterRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.KakaoLoginResponseDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.RegisterResponseDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/kakao")
    public ResponseEntity<KakaoLoginResponseDTO> kakaoLogin(@RequestBody KakaoLoginRequestDTO kakaoLoginRequestDTO) {
        return ResponseEntity.ok(userService.kakaoLogin(kakaoLoginRequestDTO));
    }

    @GetMapping("/nick-validation/{nickname}")
    public ResponseEntity<Boolean> nicknameValidation(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.checkNicknameValidation(nickname));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(userService.register(registerRequestDTO, System.currentTimeMillis()));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

    @PostMapping("/temp")
    public ResponseEntity<RegisterResponseDTO> tempLogin() {
        return ResponseEntity.ok(userService.getOrRegisterTempAccount(System.currentTimeMillis()));
    }
}