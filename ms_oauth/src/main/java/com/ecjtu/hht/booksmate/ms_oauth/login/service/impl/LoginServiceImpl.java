package com.ecjtu.hht.booksmate.ms_oauth.login.service.impl;

import com.ecjtu.hht.booksmate.common.util.BookResult;
import com.ecjtu.hht.booksmate.common.util.SHAUtil;
import com.ecjtu.hht.booksmate.common.util.entity.person.Person;
import com.ecjtu.hht.booksmate.msoauth.login.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 登录service
 */
@Service
public class LoginServiceImpl implements LoginService {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonMapper personMapper;
    /**
     * 验证验证码是否有效
     *
     * @param validateCode
     * @param session
     */
    @Override
    public BookResult validateCode(String validateCode, HttpSession session) {
        // 获得验证码对象
        Object cko = session.getAttribute("code");
        if (cko == null) {
            return BookResult.build(400,"验证码已过期！" );
        }
        String captcha = cko.toString();
        // 判断验证码输入是否正确
        if (StringUtils.isEmpty(validateCode) || captcha == null || !(validateCode.equalsIgnoreCase(captcha))) {
            return BookResult.build(400,"验证码错误，请重新输入！" );
        } else {
            return BookResult.ok();
        }
    }

    /**
     * 验证邮箱密码是否正确
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public BookResult validatePassword(String email, String password) {
        //如果都不为空
        if(StringUtils.isNotEmpty(email)&&StringUtils.isNotEmpty(password)){
            //SHA加密
            String shapassword = null;
            try {
                shapassword = SHAUtil.shaEncode(password);
            } catch (Exception e) {
                logger.error("SHA加密失败",e);
            }
            Person person = personMapper.doLogin(email, shapassword);
            if(person!=null){
              return BookResult.ok(person);
            }else{
                return BookResult.build(400,"用户名或者密码错误");
            }
        }
        return BookResult.ok();
    }
}
