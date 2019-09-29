package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-04-17
 */
public interface IDynCommentService extends IService<DynComment> {

    /**
     * 获取动态评论数
     * @param id
     */
    Integer getDynCommentCount(Integer id);

    /**
     * 保存回复的动态
     * @param comment
     * @return
     */
    BookResult saveReplyDyn(DynComment comment) throws Exception;

    /**
     * 获取所有动态评论动态
     * @param dynId
     * @return
     */
    List<DynComment> getDynAllCommentByDynId(Integer dynId);

    /**
     * 增加删除评论点赞
     * @param commentId
     * @param type
     * @return
     */
    BookResult optCommentCount(Integer commentId, Integer type);
}
