package com.ecjtu.hht.booksmate.ms_psn.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class Dynamic extends Model<Dynamic> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 创建人
     */
    @TableField("create_psn")
    private Integer createPsn;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 动态状态（0已删除 1正常）
     */
    @TableField("dyn_status")
    private Integer dynStatus;
    /**
     * 动态权限1=所有人 2=好友
     */
    private Integer permission;

    /**
     * 图片信息
     */
    private String imgInfo;

    /**
     * 标题
     */
    private String title;
    /**
     * 点赞数
     */
    private Integer awardCount;
    /**
     * 日期描述
     */
    @TableField(exist = false)
    private String dateDesc;
    /**
     * 图片路径
     */
    @TableField(exist = false)
    private List<String> imgUrls;
    /**
     * 内容限制
     */
    @TableField(exist = false)
    private Integer more;


    /**
     * 简短的内容
     */
    @TableField(exist = false)
    private String simpleContent;

    /**
     * 评论数
     */
    @TableField(exist = false)
    private Integer commentCount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
