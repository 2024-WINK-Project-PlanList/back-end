package kr.ac.kookmin.wink.planlist.user.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.user.dto.request.ChangeProfileRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.request.SongRequestDTO;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("/me")
    public ResponseEntity<?> changeUserProfile(@RequestBody ChangeProfileRequestDTO requestDTO, @AuthenticationPrincipal SecurityUser securityUser) {
        userService.changeUserProfile(requestDTO, securityUser.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.getCurrentUser(securityUser));
    }

    @PatchMapping("/me/song")
    public ResponseEntity<?> updateSong(@RequestBody SongRequestDTO requestDTO, @AuthenticationPrincipal SecurityUser securityUser) {
        userService.updateSong(requestDTO, securityUser.getUser());
        return ResponseEntity.ok().build();
    }
}