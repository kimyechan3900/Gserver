package com.example.Gserver.Websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class SocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /*@SubscribeMapping("/room/{roomId}")// 웹소켓 구독 ("room//{roomId}")으로 들어오는 거는 다 받음.
    // '/topic/room/123'식으로 url 설정하면됨.
    //구독취소는 클라이언트쪽에서 같은 url로 구독 취소하면됨.
    public void ParticipateRoom(@DestinationVariable String roomId, @Payload Map<String,String> payload, SimpMessageHeaderAccessor headerAccessor){
        String NickName = payload.get("NickName");
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client subscribed to room " + roomId + " with session ID: " + sessionId);

        template.convertAndSend(String.format("/topic/room/%s", roomId),NickName);
    }*/

    @SubscribeMapping("/Room/{roomId}")// 웹소켓 구독 ("room//{roomId}")으로 들어오는 거는 다 받음.
    // '/topic/room/123'식으로 url 설정하면됨.
    //구독취소는 클라이언트쪽에서 같은 url로 구독 취소하면됨.
    public void ParticipateRoom(@DestinationVariable String roomId ,SimpMessageHeaderAccessor headerAccessor){
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client subscribed to room " + roomId + " with session ID: " + sessionId);

        String NickName = "hello";
        template.convertAndSend(String.format("/topic/room/%s", roomId),NickName);
    }

    @MessageMapping("/Start/{roomId}")//클라이언트에서 메세지를 보내는 것.\
    // '/app/Start/123'식으로 url 설정하면됨.
    public void RoomStart(@DestinationVariable String roomId){
        System.out.println("Room Start " + roomId);

        String NickName = "hello";
        template.convertAndSend(String.format("/topic/room/%s", roomId),NickName);
    }

    @MessageMapping("/GameStart/{roomId}")//클라이언트에서 메세지를 보내는 것.\
    // '/app/GameStart/123'식으로 url 설정하면됨.
    public void GameStart(@DestinationVariable String roomId){
        System.out.println("Game Start " + roomId);

        String NickName = "Game Start";
        template.convertAndSend(String.format("/topic/room/%s", roomId),NickName);
    }

    @MessageMapping("/GetQuestion/{roomId}")
    // '/app/GetQuestion/123'식으로 url 설정하면됨.
    public void GetQuestion(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String Question = payload.get("Question");
        System.out.println("GetQuestion " + roomId);

        template.convertAndSend(String.format("/topic/room/%s", roomId),Question);
    }

    @MessageMapping("/GetItName/{roomId}")
    // '/app/GetItName/123'식으로 url 설정하면됨.
    public void GetItName(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String ItName = payload.get("ItName");
        System.out.println("GetItName " + roomId);

        template.convertAndSend(String.format("/topic/room/%s", roomId),ItName);
    }

    @MessageMapping("/CompleteAnswer/{roomId}")
    // '/app/CompleteAnswer/123'식으로 url 설정하면됨.
    public void CompleteAnswer(@DestinationVariable String roomId){
        //String NickName = payload.get("NickName");
        System.out.println("CompleteAnswer " + roomId);

        template.convertAndSend(String.format("/topic/room/%s", roomId),"CompleteAnswer");
    }

    @MessageMapping("/Exit/{roomId}")
    // '/app/Exit/123'식으로 url 설정하면됨.
    public void ExitRoom(@DestinationVariable String roomId){
        //String NickName = payload.get("NickName");
        System.out.println("Exit " + roomId);

        String NickName = "Exit";
        template.convertAndSend(String.format("/topic/room/%s", roomId),NickName);
    }
}
