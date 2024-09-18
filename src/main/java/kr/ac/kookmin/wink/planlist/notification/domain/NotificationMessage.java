package kr.ac.kookmin.wink.planlist.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationMessage {
    WELCOME(
            "Planlist에 오신 걸 환영합니다",
            "%s님의 하루를 연주해보세요!",
            NotificationType.NORMAL,
            null
    ),
    FRIEND_REQUEST(
            "친구 요청이 왔어요",
            "%s님에게 친구 요청이 왔어요",
            NotificationType.FRIEND,
            null
    ),
    CALENDAR_INVITATION(
            "공유 캘린더 초대가 왔어요",
            "%s님이 당신을 [%s]에 초대했어요.",
            NotificationType.INVITATION,
            null
    ),
    FRIEND_ACCEPTED(
            "%s님이 친구 요청을 수락했어요",
            "",
            NotificationType.NORMAL,
            null
    ),
    CALENDAR_NEW_SCHEDULE(
            "새로운 일정이 추가되었어요",
            "%s님이 당신을 [%s] \"%s\" 일정에 추가했어요.",
            NotificationType.NORMAL,
            "/calendar/%d"
    ),
    CALENDAR_ACCEPTED(
            "이제 [%s] 캘린더의 멤버가 되었어요",
            "클릭해서 공유캘린더를 확인해보세요.",
            NotificationType.NORMAL,
            "/calendar/%d"
    ),
    FRIEND_REJECTED(
            "",
            "",
            NotificationType.NORMAL,
            ""
    );

    private final String title;
    private final String message;
    private final NotificationType type;
    private final String link;
}