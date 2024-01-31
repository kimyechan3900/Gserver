package com.gserver.global.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;




    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /*
         * 스프링에서 웹소켓을 사용하기 위해서 클라이언트가 보내는 통신을 처리할 핸들러가 필요하다
         * 직접 구현한 웹소켓 핸들러 (webSocketHandler)를 웹소켓이 연결될 때, Handshake 할 주소 (/ws/chat)와 함께 addHandler 메소드의 인자로 넣어준다.
         */
        registry.addHandler(webSocketHandler, "websocket")
                .setAllowedOrigins("*");//CORS 설정
    }

}
