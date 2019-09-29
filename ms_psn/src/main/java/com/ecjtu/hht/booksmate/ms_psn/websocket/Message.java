package com.ecjtu.hht.booksmate.ms_psn.websocket;

import lombok.Data;

/**
 * @author hht
 * @date 2019/9/19 11:01
 */
@Data
public class Message {
    private Integer id;
    private String senderName;
    private String receiveName;
    private String content;
}
