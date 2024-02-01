package com.gserver.global.websocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private ChatRoom chatRoom;
    private String roomId;

    //웹소켓연결되었을때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("웹소켓이 연결됨");
    }


    //서로 데이터를 주고 받을 때 (실시간 양방향 통신)
    //방입장, 퇴장시에만 클라이언트에서 호출
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //메세지의 내용을 읽어
        String payload = message.getPayload();
        log.info("{}", payload);
        //ChatMessage 타입으로 변환하고
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        //가져온 UUID로 ChatRoom 객체를 찾고
        chatRoom = chatService.findRoomById(chatMessage.getRoomNumber());
        //메세지 타입에 따라 로직을 결정
        chatRoom.handlerEnterExit(session, chatMessage, chatService);
    }

    //방 이벤트 발생시 클라이언트에게 전송
    public void handleRoomEvent(ChatMessage chatMessage) throws Exception{

        chatRoom = chatService.findRoomById(chatMessage.getRoomNumber());
        chatRoom.handlerRoomEvent(chatMessage,chatService);
    }


    //웹소켓 연결 끊겼을때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //웹소켓이 닫히면(해당 채팅방을 나가거나, 앱을 종료했을 때)
        //해당 세션을 제거
        chatRoom.getMembers().remove(session);
        //마지막남은 한명이 나가고 session count 가 0이 되면 해당 방을 제거
        chatService.deleteRoom(roomId);
        log.info("웹소켓이 닫힘");
    }

}