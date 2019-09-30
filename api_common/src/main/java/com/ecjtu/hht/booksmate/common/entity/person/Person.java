package com.ecjtu.hht.booksmate.common.entity.person;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hht
 * @since 2019-03-07
 */
@Data
@Accessors(chain = true)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String psnName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 单位
     */
    private String department;

    /**
     * 电话
     */
    private String tel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 喜欢的书籍
     */
    private String hobbyBooks;

    private Integer account;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态 0正常 1黑名单
     */
    private Integer status;

    /**
     * SHA  加密算法之后
     */
    private String password;

    /**
     * 个人简介
     */
    private String brief;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
