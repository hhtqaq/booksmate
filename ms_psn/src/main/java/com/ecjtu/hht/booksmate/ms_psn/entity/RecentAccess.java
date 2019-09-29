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
@TableName("recent_access")
public class RecentAccess extends Model<RecentAccess> {

    private static final long serialVersionUID = 1L;

    public RecentAccess(Integer wasVisitorId, Integer visitorId) {
        this.wasVisitorId = wasVisitorId;
        this.visitorId = visitorId;
    }
    public RecentAccess(){

    }

    /**
     * 最近访问表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被访问人
     */
    @TableField("was_visitor_id")
    private Integer wasVisitorId;

    /**
     * 访客
     */
    @TableField("visitor_id")
    private Integer visitorId;

    /**
     * 访问时间
     */
    @TableField("visit_time")
    private Date visitTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
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

    public Integer getWasVisitorId() {
        return wasVisitorId;
    }

    public void setWasVisitorId(Integer wasVisitorId) {
        this.wasVisitorId = wasVisitorId;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}
