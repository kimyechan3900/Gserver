package com.gserver.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 모든 컨트롤러에서 발생할 수 있는 예외를 잡음.
public class GlobalExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버에 예상치 못한 에러가 발생하였습니다.");
    }*/

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        // CustomeException이 발생하면 내부 변수인 ErrorCode를 가져와 Response응답 반환
        ErrorCode errorCode = e.getErrorCode();


        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .statusCode(errorCode.getStatus())
                        .errorCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }



}
