package kr.ac.kookmin.wink.planlist.page.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.page.dto.MainPageResponseDTO;
import kr.ac.kookmin.wink.planlist.page.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping
    public ResponseEntity<MainPageResponseDTO> getMainPage(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        return ResponseEntity.ok(mainService.getMainPage(securityUser.getUser()));
    }
}
