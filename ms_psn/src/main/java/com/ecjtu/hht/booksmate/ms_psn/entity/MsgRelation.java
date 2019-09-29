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
 * @since 2019-04-23
 */
@Data
@Accessors(chain = true)
@TableName("msg_relation")
public class MsgRelation extends Model<MsgRelation> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发信人id
     */
    @TableField("sender_Id")
    private Integer senderId;

    /**
     *  收件人id
     */
    @TableField("receive_Id")
    private Integer receiveId;

    /**
     * 聊天状态0=正常 1=删除 再次发送时会改为0
     */
    private Integer status;

    /**
     * 最新的消息内容
     */
    @TableField("last_content")
    private String lastContent;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
