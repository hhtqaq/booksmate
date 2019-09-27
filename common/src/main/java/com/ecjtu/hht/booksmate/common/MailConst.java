package com.ecjtu.hht.booksmate.common;

/**
 * 邮件常量类
 */
public final class MailConst {
    /**
     * 注册验证码模板
     */
    public static final String REGISTER_CODE_TEMPLATE="registerCode";
    public static final String REGISTER_SUCCESS_CODE_TEMPLATE="registerSuccess";
    /**
     * 邮件模板路径
     */
   public static final String MAIL_BASE_PACKAGE_PATH= "/templates/ftl";
    /**
     * 邮件模板后缀
     */
   public static final String TEMPLATE_SUFFIX=".ftl";
    /**
     * redis邮件内容前缀
     */
   public static final String MAIL_REDIS_PREFIX="mail_";

}
