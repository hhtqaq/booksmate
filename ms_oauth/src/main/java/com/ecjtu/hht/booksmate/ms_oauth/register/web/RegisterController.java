package com.ecjtu.hht.booksmate.ms_oauth.register.web;

import com.ecjtu.hht.booksmate.common.util.BookResult;
import com.ecjtu.hht.booksmate.msoauth.login.vo.LoginForm;
import com.ecjtu.hht.booksmate.msoauth.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RegisterController {


    @Autowired
    private RegisterService registerService;
    /**
     * 发送注册时的验证码
     * @return
     */
    @GetMapping("/ajaxSendRegisterCode")
    public Map ajaxSendRegisterCodeEmail(String receive){
        Map resultMap = registerService.sendRegisterCodeEmail(receive);
        return resultMap;
    }

    /**
     * 人员注册
     * @param form
     * @param request
     * @return
     */
    @PostMapping("/oauth/register")
    public BookResult psnRegister(LoginForm form, HttpServletRequest request){
        BookResult result= registerService.psnRegister(form,request);
        return result;
    }
}
