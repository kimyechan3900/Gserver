package com.gserver.global.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/Room")
                .withSockJS() //웹소켓 연결을 하는 경로 설정
                .setInterceptors(new HandshakeInterceptor(){
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        System.out.println("웹소켓: Handshake start");
                        return true; // 이를 false로 반환하면 Handshake를 중단합니다.
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                               WebSocketHandler wsHandler, Exception exception) {
                        if (exception == null) {
                            System.out.println("웹소켓: Handshake success");
                        } else {
                            System.out.println("웹소켓: Handshake false");
                            exception.printStackTrace();
                        }
                    }
                });


        System.out.println("웹소켓 연결 초기 서버 설정 완료");
        // 1. http://localhost:8080/room을 통해 클아이언트에서 웹소켓 연결가능.
        // 이거는 웹소켓 연결을 하는 거라, 클라이언트 웹소켓 시작시 한번만 연결을 하면됨 (웹소켓 연결 -> 메세지보내기 (메세지 받고싶으면 주제 구독))
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

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
                StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
                // ignore non-STOMP messages like heartbeat messages
                if (sha.getCommand() == null) {
                    return;
                }

                String sessionId = sha.getSessionId();
                switch (sha.getCommand()) {
                    case DISCONNECT:
                        System.out.println("웹소켓: connection disconnect - " + sessionId);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
