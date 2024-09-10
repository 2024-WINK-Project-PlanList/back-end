package kr.ac.kookmin.wink.planlist.shared.exeption;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SharedError implements ErrorCode {
    TEST("테스트", HttpStatus.INTERNAL_SERVER_ERROR),;

    private final String message;
    private final HttpStatus httpStatus;
}
