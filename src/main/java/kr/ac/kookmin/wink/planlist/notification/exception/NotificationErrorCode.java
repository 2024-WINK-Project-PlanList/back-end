package kr.ac.kookmin.wink.planlist.notification.exception;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode implements ErrorCode {
    FRIEND_REQUEST_NOT_FOUND("사용자의 친구 요청 알림을 찾지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CALENDAR_INVITATION_NOT_FOUND("공유캘린더 초대 알림을 찾지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;
}
