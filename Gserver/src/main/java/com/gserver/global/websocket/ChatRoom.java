package com.gserver.global.websocket;

import com.gserver.domain.participate.Repository.PlayerRepo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomNumber;
    private Set<WebSocketSession> members = new HashSet<>();

    @Autowired
    private PlayerRepo playerRepo;

    @Builder
    public ChatRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    //방입장, 퇴장시에 호출
    public void handlerEnterExit(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {

        switch (chatMessage.getType()) {
            // 방입장 (소켓 세션 연결)
            case ENTER:
                members.add(session);

                if(playerRepo.findById(chatMessage.getPlayerId()).get().getRoom().equals(chatMessage.getRoomNumber()))
                    throw new RuntimeException(); // 추후 수정예정

                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setPlayerId(chatMessage.getPlayerId());
                chatMessage.setImage(chatMessage.getImage());

                sendMessage(chatMessage, chatService);
                break;

            // 방퇴장 (소켓 세션 해제)
            case EXIT:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setPlayerId(chatMessage.getPlayerId());

                sendMessage(chatMessage, chatService);

                members.remove(session);
                break;

            // 필요한 경우 다른 case 블록 추가
        }
    }


    private <T> void sendMessage(T message, ChatService chatService) {
        // 연결되어있는 모든 세션에 메세지 전달.
        members.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }

    public void handlerRoomEvent(ChatMessage chatMessage, ChatService chatService) {

        switch (chatMessage.getType()) {

            // 질문 답변 완료
            case ANSWER_COMPLETE:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setPlayerId(chatMessage.getPlayerId());

                sendMessage(chatMessage, chatService);
                break;

            // 질문 가져오기
            case GET_QUESTION:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setQuestion(chatMessage.getQuestion());

                sendMessage(chatMessage, chatService);
                break;

            //게임시작, 라운드 변환 (술래 체인지)
            case GAME_START:
            case ROUND_CHANGE:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setPlayerId(chatMessage.getPlayerId());
                chatMessage.setCurrentRound(chatMessage.getCurrentRound());

                sendMessage(chatMessage, chatService);
                break;

            //맞춘 정답 개수
            case CORRECT_COUNT:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());
                chatMessage.setPlayerId(chatMessage.getPlayerId());
                chatMessage.setCorrectCount(chatMessage.getCorrectCount());

                sendMessage(chatMessage, chatService);
                break;

            //게임 종료
            case FINISH:
                chatMessage.setType(chatMessage.getType());
                chatMessage.setRoomNumber(chatMessage.getRoomNumber());

                sendMessage(chatMessage, chatService);
                chatService.deleteRoom(chatMessage.getRoomNumber());
                break;
        }
    }
}
