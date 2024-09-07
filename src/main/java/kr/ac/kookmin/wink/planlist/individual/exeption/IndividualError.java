package kr.ac.kookmin.wink.planlist.individual.exeption;

import kr.ac.kookmin.wink.planlist.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IndividualError implements ErrorCode {
    //userid , calendarid , 캘린더 삭제 실패, scheduleid
    INVALID_USER_ID("유효하지 않은 유저 아이디", HttpStatus.BAD_REQUEST),
    INVALID_CALENDAR_ID("유효하지 않은 캘린더 아이디", HttpStatus.BAD_REQUEST),
    DELETE_CALENDAR_FAILED("캘린더 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_SCHEDULE_ID("유효하지 않은 스케쥴 아이디", HttpStatus.BAD_REQUEST),
    INVALID_CALENDAR("유효하지 않은 캘린더", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final HttpStatus httpStatus;
}
