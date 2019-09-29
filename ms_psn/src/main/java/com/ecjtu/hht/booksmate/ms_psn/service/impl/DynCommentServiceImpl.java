package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.util.JacksonUtils;
import com.ecjtu.hht.booksmate.ms_psn.entity.DynComment;
import com.ecjtu.hht.booksmate.ms_psn.entity.Dynamic;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;
import com.ecjtu.hht.booksmate.ms_psn.mapper.DynCommentMapper;
import com.ecjtu.hht.booksmate.ms_psn.mapper.DynamicMapper;
import com.ecjtu.hht.booksmate.ms_psn.mapper.NoticeMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.IDynCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-04-17
 */
@Service
public class DynCommentServiceImpl extends ServiceImpl<DynCommentMapper, DynComment> implements IDynCommentService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DynCommentMapper dynCommentMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 获取动态评论数
     *
     * @param id
     */
    @Override
    public Integer getDynCommentCount(Integer id) {
        DynComment dynComment = new DynComment();
        dynComment.setDynId(id);
        Wrapper wrapper = new EntityWrapper(dynComment);
        return dynCommentMapper.selectCount(wrapper);
    }

    /**
     * 保存回复的动态
     *
     * @param comment
     * @return
     */
    @Override
    @Transactional
    public BookResult saveReplyDyn(DynComment comment) throws Exception {
        Date date = new Date();
        comment.setCreateDate(date);
        comment.setAwardCount(0);
        dynCommentMapper.insert(comment);
        //发送通知
        //获取动态内容
        Dynamic dynamic = dynamicMapper.selectById(comment.getDynId());
        //如果是回复自己的动态则不发送通知
        if (dynamic.getCreatePsn() != comment.getPsnId()) {
            Notice notice = new Notice();
            notice.setType(3);
            notice.setSenderPsn(comment.getPsnId());
            notice.setReceivePsn(dynamic.getCreatePsn());
            Notice notice1 = noticeMapper.selectOne(notice);
            //msg最好保存通知消息需要的参数map形式
            Map paramsMap = new HashMap();
            paramsMap.put("dynId", dynamic.getId());
            paramsMap.put("dynContent", dynamic.getContent());
            paramsMap.put("dynTitle", dynamic.getTitle());
            paramsMap.put("commentContent", comment.getContent());
            paramsMap.put("commentId", comment.getId());
            paramsMap.put("commentDate", comment.getCreateDate());
            paramsMap.put("commentPsn", comment.getPsnId());
            //  type=2 发布动态
            paramsMap.put("type", "1");
            String msg = JacksonUtils.mapToJsonStr(paramsMap);
            //如果不为空代表曾经有过 只需要修改状态
            if (notice1 != null) {
                notice1.setStatus(1);
                notice1.setMsg(msg);
                notice1.setUpdateDate(date);
                noticeMapper.updateById(notice1);
            } else {
                notice.setCreateDate(date);
                notice.setStatus(1);
                notice.setMsg(msg);
                notice.setUpdateDate(date);
                noticeMapper.insert(notice);
            }
        }
        return BookResult.ok();
    }

    /**
     * 后去动态
     *
     * @param dynId
     * @return
     */
    @Override
    public List<DynComment> getDynAllCommentByDynId(Integer dynId) {
        return dynCommentMapper.getDynAllCommentByDynId(dynId);
    }

    /**
     * 增加删除评论点赞
     *
     * @param commentId
     * @param type
     * @return
     */
    @Override
    public BookResult optCommentCount(Integer commentId, Integer type) {
        DynComment dynComment = dynCommentMapper.selectById(commentId);
        Integer count = dynComment.getAwardCount();
        count = type == 1 ? ++count : --count;
        count = count < 0 ? 0 : count;
        dynCommentMapper.updateCount(count, commentId);
        return BookResult.ok();
    }


}
