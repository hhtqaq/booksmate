package com.ecjtu.hht.booksmate.ms_oauth.login.vo;

/**
 * 登录注册表单  登录时需要的参数都封装在这里
 */
public class LoginForm {
    private String email;
    private String password;
    private String validateCode;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
