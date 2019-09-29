package com.ecjtu.hht.booksmate.ms_psn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
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
 * @since 2019-04-17
 */
@Data
@Accessors(chain = true)
@TableName("dyn_comment")
public class DynComment extends Model<DynComment> {


    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动态id
     */
    private Integer dynId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 评论时间描述
     */
    @TableField(exist = false)
    private String dateDesc;

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @TableField(exist = false)
    private Person person;
    /**
     * 评论点赞数
     */
    private Integer awardCount;

    /**
     * 评论人Id
     */
    private Integer psnId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDynId() {
        return dynId;
    }

    public void setDynId(Integer dynId) {
        this.dynId = dynId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public Integer getPsnId() {
        return psnId;
    }

    public void setPsnId(Integer psnId) {
        this.psnId = psnId;
    }

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return null;
    }
}
