package com.ecjtu.hht.booksmate.common.entity.mail;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hht
 * @since 2019-04-01
 */
@Data
public class MailInitdata {

    private static final long serialVersionUID = 1L;

    /**
     * 书籍信息主键
     */
    private Integer id;

    /**
     * 发件人id   0代表系统发送
     */
    private Integer senderPsnId;

    /**
     * 收件人id  0代表 站外人员
     */
    private Integer receivePsnId;

    /**
     * 邮件模板
     */
    private String templateName;

    /**
     * 邮件发送状态1代表已经发送0未发送
     */
    private Integer status;

    /**
     * 邮件发送状态成功与否1=成功 0=失败
     */
    private Integer sendStatus;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 收件人邮件
     */
    private String receiveEmail;

    /**
     * 发件人邮件
     */
    private String senderEmail;

    /**
     * 描述
     */
    private String msg;
    /**
     * 邮件主题
     */
    private String subject;



}
