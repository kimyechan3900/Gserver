package com.example.Gserver.Controller;

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

    @SubscribeMapping("/room/{roomId}")
    public void ParticipateRoom(@DestinationVariable String roomId, @Payload Map<String,String> payload, SimpMessageHeaderAccessor headerAccessor){
        String NickName = payload.get("NickName");
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client subscribed to room " + roomId + " with session ID: " + sessionId);

        template.convertAndSend(String.format("/room/%s", roomId),NickName);
    }

    @MessageMapping("/Start/{roomId}")
    public void GameStart(@DestinationVariable String roomId){
        System.out.println("Game Start " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId),"GameStart");
    }

    @MessageMapping("/GetQuestion/{roomId}")
    public void ParticipateRoom(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String Question = payload.get("Question");
        System.out.println("GetQuestion " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId),Question);
    }

    @MessageMapping("/GetItName/{roomId}")
    public void GetItName(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String ItName = payload.get("ItName");
        System.out.println("GetItName " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId),ItName);
    }

    @MessageMapping("/CompleteAnswer/{roomId}")
    public void CompleteAnswer(@DestinationVariable String roomId){
        //String NickName = payload.get("NickName");
        System.out.println("CompleteAnswer " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId));
    }









}
