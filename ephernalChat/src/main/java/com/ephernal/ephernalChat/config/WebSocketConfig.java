package com.ephernal.ephernalChat.config;

import com.ephernal.ephernalChat.services.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { // 4. The Rulebook
        registry.addHandler(new ChatWebSocketHandler(), "/chat") // 5. The Main Rule
                .setAllowedOrigins("https://chat.ryuverse.fun/"); // 6. The VIP list.(Change with your web url)
    }

}
