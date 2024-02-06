package com.gserver.domain.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
public class ChatMessage {
    public enum MessageType{
        ENTER,// 방 입장
        EXIT, // 방 퇴장
        GAME_START, // 게임시작
        GET_QUESTION, // 질문 가져오기
        ANSWER_COMPLETE, //질문 답변 완료
        ROUND_CHANGE, // 라운드 변환
        CORRECT_COUNT, // 맞춘 정답 개수
        FINISH // 게임 종료

    }

    private MessageType type; // 메세지 타입
    private String roomNumber; // 방 Id값
    @Nullable
    private Long playerId; // 플레이어 Id값
    @Nullable
    private byte[] image; // 사진 이진데이터
    @Nullable
    private int currentRound; // 현재 라운드
    @Nullable
    private int correctCount; // 정답 개수
    @Nullable
    private String question; // 질문
}