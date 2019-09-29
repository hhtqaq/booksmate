package com.ecjtu.hht.booksmate.ms_notice.service.mail;


import com.ecjtu.hht.booksmate.common.api.mail.IMailInitdataService;
import com.ecjtu.hht.booksmate.common.api.mail.MailService;
import com.ecjtu.hht.booksmate.common.apiconst.MailConst;
import com.ecjtu.hht.booksmate.common.apiconst.RedisConst;
import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: hht
 * @Date: 2019/1/21 23:23
 * @Description:
 */
//alt +shift +m 重构方法
@Service
public class MailServiceImpl implements MailService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private IMailInitdataService mailInitdataService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String bulidMail(Map<String, Object> map, String mailTemplate) {
        String text = "";
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
            cfg.setClassForTemplateLoading(this.getClass(), MailConst.MAIL_BASE_PACKAGE_PATH);
            // 根据模板内容，动态把map中的数据填充进去，生成HTML
            Template template = cfg.getTemplate(mailTemplate + MailConst.TEMPLATE_SUFFIX);
            // map中的key，对应模板中的${key}表达式
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("____________构建邮件模板内容失败__________mailTemplate=" + mailTemplate + "_____" + e.getMessage());
        }
        return text;
    }

    /**
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param receive 收件人
     * @param sender
     * @return 0代表发送异常  1代表发送成功
     */
    @Override
    public Integer sendMailHtml(String subject, String content, String receive, String sender) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            buildMessageHelper(content, sender, subject, receive, msg);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            logger.error("____________邮件发送异常____________ receive:" + receive, e);
            return 0;
        }
        return 1;
    }


    @Override
    public void sendMail(MailInitdata mailInitdata) {
        //从redis中获取邮件内容
        String content = (String) redisTemplate.opsForValue().get(RedisConst.MAIL_PREFIX + mailInitdata.getId());
        String sender = mailInitdata.getSenderEmail();
        String subject = mailInitdata.getSubject();
        String receive = mailInitdata.getReceiveEmail();
        Integer id = mailInitdata.getId();
        Date date = new Date();
        String message = "";
        Integer sendStatus = 0;
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            buildMessageHelper(content, sender, subject, receive, msg);
            javaMailSender.send(msg);
            message = "邮件发送成功";
            sendStatus = 1;
        } catch (Exception e) {
            logger.error("____________邮件发送异常____________ receive:" + receive, e);
            //更新邮件发送状态
            message = "邮件发送异常:" + e.getMessage();
            sendStatus = 2;
        } finally {
            mailInitdataService.updateMailStatus(id, message, date, sendStatus);
        }
    }

    private void buildMessageHelper(String content, String sender, String subject, String receive, MimeMessage msg) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        //指定发送html格式的文件
        helper.setText(content, true);
        helper.setFrom(StringUtils.isEmpty(sender) ? "1293115411@qq.com" : sender);
        helper.setSubject(subject);
        helper.setTo(receive);
    }


}
