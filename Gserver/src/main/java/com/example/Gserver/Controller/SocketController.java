package com.example.Gserver.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @SubscribeMapping("/room/{roomId}")// 웹소켓 구독 ("room//{roomId}")으로 들어오는 거는 다 받음.
    // '/topic/room/123'식으로 url 설정하면됨.
    //구독취소는 클라이언트쪽에서 같은 url로 구독 취소하면됨.
    public void ParticipateRoom(@DestinationVariable String roomId, @Payload Map<String,String> payload, SimpMessageHeaderAccessor headerAccessor){

        String NickName = payload.get("NickName");
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Client subscribed to room " + roomId + " with session ID: " + sessionId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("NickName",NickName);


        template.convertAndSend(String.format("/topic/room/%s", roomId),"Participate",headers);
    }

    @MessageMapping("/Start/{roomId}")//클라이언트에서 메세지를 보내는 것.
    // '/app/Start/123'식으로 url 설정하면됨.
    public void GameStart(@DestinationVariable String roomId){
        System.out.println("Game Start " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId),"GameStart");
    }

    @MessageMapping("/GetQuestion/{roomId}")
    // '/app/GetQuestion/123'식으로 url 설정하면됨.
    public void GetQuestion(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String Question = payload.get("Question");
        System.out.println("GetQuestion " + roomId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Question",Question);

        template.convertAndSend(String.format("/room/%s", roomId),"Question",headers);
    }

    @MessageMapping("/GetItName/{roomId}")
    // '/app/GetItName/123'식으로 url 설정하면됨.
    public void GetItName(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String ItName = payload.get("ItName");
        System.out.println("GetItName " + roomId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("ItName",ItName);

        template.convertAndSend(String.format("/room/%s", roomId),"ItName",headers);
    }


    @MessageMapping("/CompleteAnswer/{roomId}")
    // '/app/CompleteAnswer/123'식으로 url 설정하면됨.
    public void CompleteAnswer(@DestinationVariable String roomId){
        //String NickName = payload.get("NickName");
        System.out.println("CompleteAnswer " + roomId);

        template.convertAndSend(String.format("/room/%s", roomId),"CompleteAnswer");
    }

    @MessageMapping("/Exit/{roomId}")
    // '/app/CompleteAnswer/123'식으로 url 설정하면됨.
    public void Exit(@DestinationVariable String roomId, @Payload Map<String,String> payload){
        String NickName = payload.get("NickName");
        System.out.println("ExitPalyer " + roomId +" "+NickName);
        template.convertAndSend(String.format("/room/%s", roomId),"NickName");
    }

    @MessageMapping("/Finish/{roomId}")
    // '/app/CompleteAnswer/123'식으로 url 설정하면됨.
    public void Finish(@DestinationVariable String roomId){
        System.out.println("Finish " + roomId);

        Map<String, Object> headers = new HashMap<>();
        headers.put("RoomId",roomId);

        template.convertAndSend(String.format("/room/%s", roomId),"Finish",headers);
    }

}
