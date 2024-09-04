package kr.ac.kookmin.wink.planlist.friend.exception;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FriendErrorCode implements ErrorCode {
    INVALID_USER_ID("유효하지 않은 사용자 ID입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FRIENDSHIP_ID("유효하지 않은 친구관계 ID입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
