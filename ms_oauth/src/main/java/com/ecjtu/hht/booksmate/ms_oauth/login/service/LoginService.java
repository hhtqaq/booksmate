package com.ecjtu.hht.booksmate.ms_oauth.login.service;


import com.ecjtu.hht.booksmate.common.util.BookResult;

import javax.servlet.http.HttpSession;

public interface LoginService {


    /**
     * 验证验证码时候有效
     * @param validateCode
     * @param session
     */
    BookResult validateCode(String validateCode, HttpSession session);

    /**
     *  验证邮箱密码是否正确
     * @param email
     * @param password
     * @return
     */
   BookResult validatePassword(String email, String password);
}
