package com.ecjtu.hht.booksmate.ms_psn.service;

import com.baomidou.mybatisplus.service.IService;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hht
 * @since 2019-04-11
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 获取所有的通知
     * @param id
     * @return
     */
    Map<String, List<Notice>> getAllNotice(Integer id);

    /**
     * 忽略通知
     * @param id
     * @return
     */
    BookResult ignoreNotice(Integer id);

    /**
     * 标记所有通知为已读
     * @param noticeType 通知类型
     * @param id   需要删除的个人id
     * @return
     */
    BookResult markAllReadedNotice(Integer noticeType, Integer id);
}
