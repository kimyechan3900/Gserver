package com.example.Gserver.Error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C_001", "서버에 문제가 발생하였습니다."),
    DUPLICATED_ROOMNUMBER(400, "AU_001", "이미 존재하는 방입니다."),
    DUPLICATED_PARTICIPATION(400, "AU_002", "이미 같은 닉네임의 사용자가 존재합니다."),
    NOT_EXIST_ROOM(400,"AU_003", "방이 존재하지 않습니다."),
    NOT_EXIST_PARTICIPATION(400,"AU_004", "해당 사용자가 존재하지 않습니다."),
    INVALID_INPUT_VALUE(400,"AU_005","요청 파라미터가 잘못되었습니다."),
    BAD_LOGIN(400, "AU_004", "잘못된 아이디 또는 패스워드입니다."),
    INVALID_PASSWORD(400, "AU_005", "잘못된 패스워드입니다."),
    NOT_EXIST_QUESTION(500, "AU_006", "저장된 질문이 없습니다"),
    INVALID_INPUT_LIST(400, "AU_007", "질문답변 리스트가 잘못되었습니다."),
    INVALID_LIST(500, "AU_008", "질문답변 저장 데이터가 잘못되었습니다.")
    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}