package kr.ac.kookmin.wink.planlist.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponseDTO {
    private String code;
    private String message;
    private Integer status;
    private Instant timestamp;

    public static ErrorResponseDTO create(ErrorCode errorCode, Instant timestamp) {
        return ErrorResponseDTO.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .timestamp(timestamp)
                .build();
    }
}