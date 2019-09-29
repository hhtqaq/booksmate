package com.ecjtu.hht.booksmate.ms_psn.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecjtu.hht.booksmate.common.entity.common.BookResult;
import com.ecjtu.hht.booksmate.common.entity.person.Person;
import com.ecjtu.hht.booksmate.common.util.DateUtil;
import com.ecjtu.hht.booksmate.ms_psn.entity.Notice;
import com.ecjtu.hht.booksmate.ms_psn.mapper.NoticeMapper;
import com.ecjtu.hht.booksmate.ms_psn.service.INoticeService;
import com.ecjtu.hht.booksmate.common.api.person.IPersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hht
 * @since 2019-04-11
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private IPersonService personService;
    /**
     * 获取所有的通知
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, List<Notice>> getAllNotice(Integer id) {
        List<Notice> notices = noticeMapper.selectAllNotice(id);
        Map<String, List<Notice>> map = new HashMap<>();
        //关注通知列表
        List<Notice> follerNotices = new ArrayList<>();
        //私信通知列表
        List<Notice> msgNotices = new ArrayList<>();
        //动态列表
        List<Notice> dynNotices = new ArrayList<>();
        notices.forEach(notice -> {
            Person senderPsn = personService.selectById(notice.getSenderPsn());
            notice.setSenderName(senderPsn.getPsnName());
            notice.setSenderAvator(senderPsn.getAvatar());
            notice.setDateDesc(DateUtil.getTimeFormatText(notice.getUpdateDate()));
            String msg = notice.getMsg();
            if(StringUtils.isNotEmpty(msg)){
               // notice.setParamsMap((Map) JSONUtils.parse(msg));
            }
            /**
             * 通知类型1=私信 2=关注 3=动态
             */
            if (notice.getType()==1){
                msgNotices.add(notice);
            }else if(notice.getType()==2){
                follerNotices.add(notice);
            }else{
                dynNotices.add(notice);
            }
        });
        map.put("dynNotices",dynNotices);
        map.put("follerNotices",follerNotices);
        map.put("msgNotices",msgNotices);
        return map;
    }

    /**
     * 忽略通知
     * 只需要更新状态
     * @param id
     * @return
     */
    @Override
    @Transactional
    public BookResult ignoreNotice(Integer id) {
        noticeMapper.updateNoticeStatus(0,id,new Date());
        return BookResult.ok();
    }

    /**
     * 标记所有通知为已读
     *
     * @param noticeType
     * @param id
     * @return
     */
    @Override
    @Transactional
    public BookResult markAllReadedNotice(Integer noticeType, Integer id) {
        noticeMapper.deleteAllNoticeByType(noticeType,id);
        return BookResult.ok();
    }
}
