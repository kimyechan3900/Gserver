package com.example.Gserver.Error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C_001", "서버에 문제가 발생하였습니다."),
    DUPLICATED_ROOMNUMBER(400, "AU_001", "이미 존재하는 방입니다."),
    DUPLICATED_PARTICIPATION(400, "AU_002", "이미 같은 닉네임의 플레이어가 존재합니다."),
    NOT_EXIST_ROOM(400,"AU_003", "방이 존재하지 않습니다."),
    NOT_EXIST_PARTICIPATION(400,"AU_004", "해당 플레이어가 존재하지 않습니다."),
    NOT_EXIST_HOST(400,"AU_005", "방장이 존재하지 않습니다."),
    NOT_EXIST_QUESTION(500,"AU_006", "기본 질문이 존재하지 않습니다."),
    EMPTY_ROOM(500,"AU_007","해당 방의 플레이어가 존재하지 않습니다."),
    ALREADY_GAME_START(400,"AU_008","이미 게임 진행 중인 방입니다."),
    NOT_EXIT_ROOM(400,"AU_009","게임 진행중엔 나갈 수 없습니다.")
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