package com.ecjtu.hht.booksmate.ms_psn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author hht
 * @since 2019-04-11
 */
@Data
@Accessors(chain = true)
public class Notice extends Model<Notice> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 通知类型1=私信2=关注3=动态
     */
    private Integer type;

    /**
     * 发送通知的人
     */
    @TableField("sender_psn")
    private Integer senderPsn;

    /**
     * 接收通知的人
     */
    @TableField("receive_psn")
    private Integer receivePsn;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;


    /**
     * 状态 1=关注  0=取消关注
     */
    private Integer status;


    /**
     * 通知内容
     */
    @TableField("msg")
    private String msg;
    @TableField(exist = false)
    private String senderName;
    @TableField(exist = false)
    private String senderAvator;

    public Map getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map paramsMap) {
        this.paramsMap = paramsMap;
    }

    @TableField(exist = false)
    private Map paramsMap;
    /**
     * 时间描述  显示几秒前
     */
    @TableField(exist = false)
    private String dateDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAvator() {
        return senderAvator;
    }

    public void setSenderAvator(String senderAvator) {
        this.senderAvator = senderAvator;
    }

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return null;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 通知类型1=私信 2=关注 3=动态
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSenderPsn() {
        return senderPsn;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSenderPsn(Integer senderPsn) {
        this.senderPsn = senderPsn;
    }

    public Integer getReceivePsn() {
        return receivePsn;
    }

    public void setReceivePsn(Integer receivePsn) {
        this.receivePsn = receivePsn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
