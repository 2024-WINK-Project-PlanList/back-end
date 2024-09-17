package kr.ac.kookmin.wink.planlist.notification.dto;

import kr.ac.kookmin.wink.planlist.notification.domain.Notification;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private NotificationType type;
    private Long referenceId;
    private String title;
    private String message;
    private String link;
    private boolean isRead;
    private String imagePath;
    private LocalDateTime createdAt;

    public static NotificationDTO create(Notification notification, String imagePath, String title, String message) {
        NotificationMessage notificationMessage = notification.getMessage();
        Long referenceId = notification.getReferenceId();

        return NotificationDTO
                .builder()
                .id(notification.getId())
                .type(notificationMessage.getType())
                .title(title)
                .message(message)
                .link(notificationMessage.getLink().formatted(referenceId))
                .isRead(notification.isRead())
                .imagePath(imagePath)
                .referenceId(referenceId)
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
