package kr.ac.kookmin.wink.planlist.shared.exeption;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SharedErrorCode implements ErrorCode {
    TEST("테스트", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CALENDAR_ID("잘못된 캘린더 아이디입니다", HttpStatus.BAD_REQUEST),
    CALENDAR_DELETE_FAILED("캘린더 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVITE_REJECT_FAILED("캘린더 초대 거절에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_ACCEPTED("이미 참여중인 캘린더입니다.", HttpStatus.BAD_REQUEST),
    LEAVE_FAILED("캘린더 탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_SCHEDULE_ID("잘못된 스케쥴 아이디", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final HttpStatus httpStatus;
}
