package com.ecjtu.hht.booksmate.ms_psn.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hht
 * @since 2019-04-11
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> selectAllNotice(Integer id);

    void deleteAllNoticeByType(Integer noticeType, Integer id);

    void updateNoticeStatus(int status, Integer noticeId, Date date);
}
