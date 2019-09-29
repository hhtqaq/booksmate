package com.ecjtu.hht.booksmate.common.api.mail;

import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;

import java.io.File;
import java.util.Map;

/**
 * @Auther: hht
 * @Date: 2019/1/21 23:15
 * @Description:
 */
public interface MailService {

    /**
     * 通过freemarker
     * 构建发送邮件的内容
     *
     * @param map          模板参数
     * @param mailTemplate 模板名称
     * @return
     */
    String bulidMail(Map<String, Object> map, String mailTemplate);

    /**
     * 发送html的邮件
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param receive 收件人
     */
    Integer sendMailHtml(String subject, String content, String receive, String sender);


    void sendMail(MailInitdata mailInitdata);

}
