package kr.ac.kookmin.wink.planlist.user.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.user.dto.request.ChangeProfileRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.SongRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserInfoResponseDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("/me")
    public ResponseEntity<?> changeUserProfile(
            @RequestPart ChangeProfileRequestDTO profile,
            @RequestPart(required = false) MultipartFile image,
            @AuthenticationPrincipal SecurityUser securityUser) {
        userService.changeUserProfile(profile, image, securityUser.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDTO> getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.getCurrentUserInfo(securityUser));
    }

    @PatchMapping("/me/song")
    public ResponseEntity<?> updateSong(@RequestBody SongRequestDTO requestDTO, @AuthenticationPrincipal SecurityUser securityUser) {
        userService.updateSong(requestDTO, securityUser.getUser().getId());
        return ResponseEntity.ok().build();
    }
}