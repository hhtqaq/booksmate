package com.ecjtu.hht.booksmate.ms_oauth.register.service;




import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.ms_oauth.login.vo.LoginForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RegisterService {
    /**
     *
     * @return
     */
     Map<String,String> sendRegisterCodeEmail(String receive);

    /**
     * 人员注册
     * @param form
     * @param request
     * @return
     */
    BookResult psnRegister(LoginForm form, HttpServletRequest request);
}
