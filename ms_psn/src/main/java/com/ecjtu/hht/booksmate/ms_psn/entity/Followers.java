package com.ecjtu.hht.booksmate.ms_psn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
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
public class Followers extends Model<Followers> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员
     */
    @TableField("psn_id")
    private Integer psnId;

    /**
     * 关注人员
     */
    @TableField("follower_id")
    private Integer followerId;

    /**
     * 关注时间
     */
    @TableField("follow_time")
    private Date followTime;

    /**
     * 关注状态（0未关注，1已关注）
     */
    @TableField("follow_status")
    private Integer followStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPsnId() {
        return psnId;
    }

    public void setPsnId(Integer psnId) {
        this.psnId = psnId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }
}
