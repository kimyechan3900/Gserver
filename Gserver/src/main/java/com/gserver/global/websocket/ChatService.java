package com.gserver.global.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gserver.domain.room.Model.Room;
import com.gserver.domain.room.Repository.RoomRepo;
import com.gserver.global.error.CustomException;
import com.gserver.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;

    @Autowired
    private RoomRepo roomRepo;

    private Map<String, ChatRoom> chatRooms = new HashMap<>();


    //활성화된 모든 채팅방을 조회
    /*public List<ChatDto> findAllRoom() {
        List<ChatDto> collect = chatRooms.values().stream().map(chatRoom -> new ChatDto(chatRoom.getRoomId(), chatRoom.getName(), (long) chatRoom.getSessions().size())).collect(Collectors.toList());
        return collect;
    }*/

    //채팅방 하나를 조회
    public ChatRoom findRoomById(String roomNumber) {
        return chatRooms.get(roomNumber);
    }


    //새로운 방 생성
    public ChatRoom createRoom(String roomNumber) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomNumber(roomNumber)
                .build();

        chatRooms.put(roomNumber, chatRoom);
        return chatRoom;
    }

    //방 삭제
    public void deleteRoom(String roomNumber) {
        //해당방에 아무도 없다면 자동 삭제
        chatRooms.remove(roomNumber);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}