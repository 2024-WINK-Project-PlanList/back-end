package kr.ac.kookmin.wink.planlist.todolist.exception;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TodolistErrorCode implements ErrorCode {
    INVALID_TODOLIST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
    INVALID_TODOLIST_UPDATE(HttpStatus.BAD_REQUEST, "잘못된 업데이트?"),
    INVALID_TODOLIST_CHECK(HttpStatus.BAD_REQUEST, "잘못된 조회"),
    INVALID_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 사용자 ID입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
