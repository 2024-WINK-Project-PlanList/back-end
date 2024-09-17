package kr.ac.kookmin.wink.planlist.user.exception;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_KAKAO_ACCESS_TOKEN("유효하지 않은 Kakao API 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_USER_ID("유효하지 않은 사용자 ID입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
