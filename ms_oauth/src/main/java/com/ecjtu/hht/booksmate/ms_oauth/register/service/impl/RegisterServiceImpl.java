package com.ecjtu.hht.booksmate.ms_oauth.register.service.impl;

import com.ecjtu.hht.booksmate.common.api.mail.IMailInitdataService;
import com.ecjtu.hht.booksmate.common.api.mail.MailService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import com.ecjtu.hht.booksmate.common.apiconst.MailConst;
import com.ecjtu.hht.booksmate.common.apiconst.RedisConst;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.mail.MailInitdata;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.util.CodeGeneratorUtil;
import com.ecjtu.hht.booksmate.common.util.FileNameUtils;
import com.ecjtu.hht.booksmate.common.util.GeneratorNamePicUtil;
import com.ecjtu.hht.booksmate.common.util.SHAUtil;
import com.ecjtu.hht.booksmate.ms_oauth.login.vo.LoginForm;
import com.ecjtu.hht.booksmate.ms_oauth.register.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MailService mailService;
    @Autowired
    private IMailInitdataService mailInitdataService;
    @Autowired
    private IPersonService personService;
    @Value("${avatorRoot}")
    private String avatorRoot;
    @Value("${fileServer}")
    private String fileServer;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param receive 收件人
     * @return
     */
    @Override
    public Map<String, String> sendRegisterCodeEmail(String receive) {
        Map<String, String> map = new HashMap();
        //保存邮件记录
        MailInitdata mailInitdata = new MailInitdata();
        try {
            //redis存入动态code
            Integer code = saveRedis(receive);
            //构建邮件模板参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("code", code);
            String mailContent = mailService.bulidMail(paramMap, MailConst.REGISTER_CODE_TEMPLATE);
            String subject = "图书之友-注册";
            mailInitdata.setSenderPsnId(0);//表示系统发送
            mailInitdata.setStatus(1);//表明已经发送
            mailInitdata.setSendStatus(1);
            mailInitdata.setSubject(subject);
            Date date = new Date();
            mailInitdata.setCreateDate(date);
            mailInitdata.setReceivePsnId(0);//站外人员
            mailInitdata.setTemplateName(MailConst.REGISTER_CODE_TEMPLATE);
            mailInitdata.setUpdateDate(date);
            mailInitdata.setReceiveEmail(receive);
            Integer sendStatus = mailService.sendMailHtml(subject, mailContent, receive, null);
            mailInitdata.setSendStatus(sendStatus);
            map.put("info", "发送成功");
            mailInitdata.setMsg("发送成功");
        } catch (Exception e) {
            map.put("info", "发送失败,请重新发送");
            mailInitdata.setMsg(e.getMessage());
            mailInitdata.setSendStatus(0);
            logger.error("___________________________发送注册失败____receive" + e);
        }
        mailInitdataService.saveMailInitDate(mailInitdata);
        return map;
    }

    /**
     * 人员注册
     *
     * @param form
     * @param request
     * @return
     */
    @Override
    public BookResult psnRegister(LoginForm form, HttpServletRequest request) {
        try {
            //验证是否正确
            Integer registerCode = (Integer) redisTemplate.opsForValue().get(RedisConst.REGISTER_CODE + form.getEmail());
            if (registerCode == null) {
                return BookResult.build(400, "验证码已失效，请重新发送");
            }
            if (!registerCode.toString().equals(form.getValidateCode())) {
                return BookResult.build(400, "验证码错误");
            }
            String fileName = FileNameUtils.generatorFileName();
            //生成默认头像
            GeneratorNamePicUtil.generateImg(form.getName(), avatorRoot, fileName);
            Person person = new Person();
            String enPassword = SHAUtil.shaEncode(form.getPassword());
            person.setPassword(enPassword);
            person.setEmail(form.getEmail());
            person.setCreateDate(new Date());
            person.setUpdateDate(new Date());
            person.setAvatar(fileServer + "avator/" + fileName + ".jpg");
            person.setRole(0);
            person.setAccount(0);
            person.setStatus(0);
            person.setPsnName(form.getName());
            //保存用户
            boolean insert = personService.insert(person);
            //发送注册成功邮件
            sendRegisterSuccessMail(person);
            //存取session
            request.getSession().setAttribute("person", person);
        } catch (Exception e) {
            logger.error("______________人员注册失败__email=" + form.getEmail(), e);
            return BookResult.build(400, "注册失败");
        }
        return BookResult.build(200, "注册成功");
    }

    /**
     * 发送注册成功的邮件
     *
     * @param person
     */
    private void sendRegisterSuccessMail(Person person) {
        MailInitdata mailInitdata = new MailInitdata();
        mailInitdata.setCreateDate(new Date());
        mailInitdata.setSenderPsnId(0);
        mailInitdata.setReceivePsnId(person.getId());
        //构建邮件内容
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("psnName", person.getPsnName());
        String content = mailService.bulidMail(paramMap, MailConst.REGISTER_SUCCESS_CODE_TEMPLATE);
        mailInitdata.setStatus(0);//设置未发送状态  由任务发送
        mailInitdata.setTemplateName(MailConst.REGISTER_SUCCESS_CODE_TEMPLATE);
        mailInitdata.setReceiveEmail(person.getEmail());
        mailInitdata.setSubject("图书之友-注册成功");
        mailInitdataService.saveMailInitDate(mailInitdata);
        //保存内容到redis中
        redisTemplate.opsForValue().set(RedisConst.MAIL_PREFIX+ mailInitdata.getId(), content);
    }

    /**
     * 生成验证码
     *
     * @return
     */
    private Integer saveRedis(String receive) {
        Integer registerCode = (Integer) redisTemplate.opsForValue().get(RedisConst.REGISTER_CODE+ receive);
        //如果为空
        if (registerCode == null) {
            //重新生成
            registerCode = CodeGeneratorUtil.generatorCode();
            //放到redis中 并设置过期时间为1分钟
            redisTemplate.opsForValue().set(RedisConst.REGISTER_CODE + receive, registerCode);
            redisTemplate.expire(RedisConst.REGISTER_CODE+ receive, 1, TimeUnit.MINUTES);
        }
        return registerCode;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println((int) ((Math.random() * 9 + 1) * 100000));
        }
    }
}
