package com.example.Gserver.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버에 예상치 못한 에러가 발생하였습니다.");
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        // 이 예제에서는 간단하게 메시지만 반환하고 있지만, 실제 애플리케이션에서는
        // errorCode, message 및 기타 관련 정보를 포함하는 객체를 반환할 수도 있습니다.
        return ResponseEntity.status(errorCode.getStatus())
                .body(new ErrorResponse(e.getErrorCode().getStatus(),e.getErrorCode().getCode(),e.getErrorCode().getMessage()));
    }



}
