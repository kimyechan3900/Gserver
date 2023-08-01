package com.example.Gserver.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/room").withSockJS(); //웹소켓 연결을 하는 경로 설정
        // 1. http://localhost:8080/room을 통해 클아이언트에서 웹소켓 연결가능.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // app 경로는 추가적인 가공을 통해 구독자들에게 전달.
        // -> @MessageMapping으로 이동 + @SendTo로 메세지 이동 방향 설정 가능
        // -> 컨트롤러에서 접두사로 /app이 맨앞 url에 붙음.

        registry.enableSimpleBroker("/topic"); // topic 경로는 가공없이 바로 구독자들에게 전달.
        //topic은 주로 구독할때 쓰임.
        //두 코드다 접두사를 설정하는 것임 -> /app , /topic 둘다 접두사임.
    }
}
