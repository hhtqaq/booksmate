/*
package com.ecjtu.hht.booksmate.ms_psn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

*/
/**
 * @author hht
 * @date 2019/9/19 9:15
 *//*

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    */
/**
     * 实现configureMessageBroker()方法，来配置消息代理。
     * 在这个方法中，我们先调用enableSimpleBroker()来创建一个基于内存的消息代理，
     * 他表示以/topic为前缀的消息将发送回客户端。接着设置一个请求路由前缀，
     * 它绑定了@MessageMapping（这个后面会用到）注解，表示以/server为前缀的消息，会发送到服务器端。
     *
     * @param config
     *//*

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //订阅广播 Broker（消息代理）名称
        config.enableSimpleBroker("/queue");
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
        config.setApplicationDestinationPrefixes("/server");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        config.setUserDestinationPrefix("/user");
    }

    */
/**
     * 最后实现了registerStompEndpoints()方法，用来注册/websocket-server端点来建立服务器。
     *
     * @param registry
     *//*

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册STOMP协议节点，同时指定使用SockJS协议
        registry
                .addEndpoint("/websocket-server")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
*/
