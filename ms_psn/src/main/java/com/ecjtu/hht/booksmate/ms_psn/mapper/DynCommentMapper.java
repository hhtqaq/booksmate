package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-04-17
 */
public interface DynCommentMapper extends BaseMapper<DynComment> {

    List<DynComment> getDynAllCommentByDynId(Integer dynId);

    @Update("update dyn_comment set awardCount=#{count} where id=#{commentId}")
    void updateCount(Integer count, Integer commentId);
}
