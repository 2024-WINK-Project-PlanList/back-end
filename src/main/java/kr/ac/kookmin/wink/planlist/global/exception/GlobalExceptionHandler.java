package kr.ac.kookmin.wink.planlist.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.create(errorCode, Instant.now());
        HttpStatus httpStatus = errorCode.getHttpStatus();

        return new ResponseEntity<>(errorResponseDTO, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception) {
        ErrorCode errorCode = GlobalErrorCode.DEFAULT;
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.create(exception, Instant.now());
        HttpStatus httpStatus = errorCode.getHttpStatus();

        exception.printStackTrace();

        return new ResponseEntity<>(errorResponseDTO, httpStatus);
    }
}
