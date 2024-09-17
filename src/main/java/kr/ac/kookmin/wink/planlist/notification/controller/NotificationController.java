package kr.ac.kookmin.wink.planlist.notification.controller;

import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.notification.dto.NotificationDTO;
import kr.ac.kookmin.wink.planlist.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        return ResponseEntity.ok(notificationService.getNotifications(securityUser.getUser()));
    }

    @PatchMapping
    public ResponseEntity<?> readNotifications(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        notificationService.setNotificationRead(securityUser.getUser());
        return ResponseEntity.ok().build();
    }
}
