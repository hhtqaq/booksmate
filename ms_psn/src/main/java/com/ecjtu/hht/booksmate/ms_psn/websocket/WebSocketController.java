package com.ecjtu.hht.booksmate.ms_psn.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hht
 * @date 2019/9/19 11:00
 */
@Slf4j
@RestController
public class WebSocketController {
    //注入SimpMessagingTemplate 用于点对点消息发送
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static final String DEFAULT_NAME = "Michael";

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        log.error("消息为" + message.getContent());
        return message;
    }

    //这里是客户端发送消息对应的路径，
    // 等于configureMessageBroker中配置的setApplicationDestinationPrefixes
    // + 这路径即 /server/sendPrivateMessage
    @SendTo("/topic/chat")
    @MessageMapping("/sendPrivateMessage")
    public Message sendPrivateMessage(@Payload Message msg) {
        log.error("消息为" + msg.getContent());
        //将消息推送到指定路径上
        return msg;
    }


    /**
     * 开启STOMP协议来传输基于代理的消息，这时控制器支持使用@MessageController，就像使用@RequestMapping是一样的
     * 当浏览器向服务端发送请求时，通过@MessageController映射/chat这个路径
     * 在SpringMVC中，可以直接在参数中获得principal,其中包含当前用户的信息
     *
     * @param msg       String
     */
    @MessageMapping("/chat")
    public void handleChat(Message msg) {
        //下面的代码就是如果发送人是Michael，接收人就是Janet，发送的信息是message，反之亦然。
        if (DEFAULT_NAME.equals(msg.getSenderName())) {
            //通过SimpMessagingTemplate的convertAndSendToUser向用户发送消息。
            //第一参数表示接收信息的用户，第二个是浏览器订阅的地址，第三个是消息本身
            simpMessagingTemplate.convertAndSendToUser(msg.getReceiveName(), "/queue/notifications",
                    msg.getContent());
        }
    }
}
