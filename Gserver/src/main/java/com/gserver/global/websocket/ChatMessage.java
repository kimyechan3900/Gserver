package com.gserver.global.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ENTER,// 방 입장
        EXIT, // 방 퇴장
        GAME_START, // 게임시작
        ANSWER_COMPLETE, //질문 답변 완료
        ROUND_CHANGE, // 라운드 변환
        CORRECT_COUNT, // 맞춘 정답 개수
        FINISH // 게임 종료

    }

    private MessageType type;
    private String roomNumber;
    private String message;
}