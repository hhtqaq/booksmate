package com.ecjtu.hht.booksmate.ms_psn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("private_letters")
public class PrivateLetters extends Model<PrivateLetters> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发送人（真实）
     */
    @TableField("sender_psn_id")
    private Integer senderPsnId;

    /**
     * 接收人（真实）
     */
    @TableField("receive_psn_id")
    private Integer receivePsnId;

    /**
     * 发送者id(仅记录
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 接受者id(仅记录
     */
    @TableField("friend_id")
    private Integer friendId;

    /**
     * 状态（1已读2未读3删除）
     */
    private Integer status;
    /**
     * 内容
     */
    private String content;
    /**
     * 日期
     */
    @TableField("send_date")
    private Date sendDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderPsnId() {
        return senderPsnId;
    }

    public void setSenderPsnId(Integer senderPsnId) {
        this.senderPsnId = senderPsnId;
    }

    public Integer getReceivePsnId() {
        return receivePsnId;
    }

    public void setReceivePsnId(Integer receivePsnId) {
        this.receivePsnId = receivePsnId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
