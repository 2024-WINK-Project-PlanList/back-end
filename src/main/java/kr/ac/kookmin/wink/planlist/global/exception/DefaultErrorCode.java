package kr.ac.kookmin.wink.planlist.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DefaultErrorCode implements ErrorCode {
    DEFAULT("응답 처리 중, 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    DefaultErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}