/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msbroker;

/**
 *
 * @author 2098167
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${host}")
    private String host;
    
    @Value("${port}")
    private int port;
    
    @Value("${clientLogin}")
    private String clientLogin;
    @Value("${clientPasscode}")
    private String clientPasscode;
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //config.enableSimpleBroker("/topic");
        config.enableStompBrokerRelay("/topic/").setRelayHost(host).setRelayPort(port).
                setClientLogin(clientLogin).
                setClientPasscode(clientPasscode).
                setSystemLogin(clientLogin).
                setSystemPasscode(clientPasscode).
                setVirtualHost(clientLogin);
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/stompendpoint").withSockJS();
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("*").withSockJS();
    }

}
